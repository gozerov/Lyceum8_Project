package ru.gozerov.lyceum8.data.rest

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import ru.gozerov.lyceum8.data.cache.entity.DataNews
import ru.gozerov.lyceum8.data.cache.room.RoomNewsRepository
import javax.inject.Inject

class NewsSourceImpl @Inject constructor(
    private val roomNewsRepository: RoomNewsRepository
): NewsSource {

    private var doc: Document? = null

    override suspend fun getNewsByPage(page: Int): List<DataNews> = withContext(Dispatchers.IO){
        val start = page * PAGE_SIZE
        doc = Jsoup.connect("$BASE_URL?start=$start").get()
        val news = mutableListOf<DataNews>()
        doc?.let { d ->
            val part1 = parsePageAndGetNews(d, "leading-@ clearfix")
            val part2 = parsePageAndGetNews(d, "items-row cols-1 row-@ row-fluid")
            news.addAll(part1)
            news.addAll(part2)
        }
        doc = null
        val roomNews = roomNewsRepository.getRecentNews().firstOrNull()
        if (page == 0 && roomNews != null && roomNews==news)
            return@withContext roomNewsRepository.getRecentNews().first()
        else
            return@withContext news
    }

    override suspend fun getNewsByUrl(url: String): DataNews = withContext(Dispatchers.IO) {
        val doc = Jsoup.connect(url).get()
        val dataNews = parseNewsPage(doc)
        return@withContext DataNews(dataNews.id, dataNews.url, dataNews.images, dataNews.title, dataNews.description)
    }

    private fun findUrlInHeader(headerDiv: String): String {
        val start = headerDiv.indexOf("\"")
        var rawUrl = ""
        for (i in start+1 until headerDiv.length) {
            if (headerDiv[i]!='\"')
                rawUrl+=headerDiv[i]
            else
                break
        }
        return "http://lyceum8.perm.ru/$rawUrl"
    }

    private suspend fun parsePageAndGetNews(doc: Document, divTextRaw: String): List<DataNews> = withContext(Dispatchers.IO) {
        var isImageContains = false
        val news = mutableListOf<DataNews>()
        var num = 0
        val changingNumIndex = divTextRaw.indexOf('@')
        var divText = divTextRaw.replace("@", "$num")
        while (true) {
            divText = divText.replace("@", "$num")
            val div = doc.select("div[class=$divText]")
            val headerDiv = div.select("h2").firstOrNull() ?: break
            val header = headerDiv.text()
            val start = div.html().indexOf("</h2>")
            var text = div.html().removeRange(0..start+4)
            val newsUrl = findUrlInHeader(headerDiv.html())
            text = text.replace("&nbsp;","")
            val end = text.indexOf("<div class=\"jcomments-links\">")
            text = text.removeRange(end until text.length)
            text = text.replace("<br>", "\n")
            while (text.contains("<img")) {
                val s = text.indexOf("<img")
                for (i in s..text.length) {
                    if (text[i]=='>') {
                        text = text.removeRange(s..i)
                        break
                    }
                }
            }
            while (text.contains("<a href")) {
                val s = text.indexOf("<a href")
                for (i in s..text.length) {
                    if (text[i]=='>') {
                        text = text.removeRange(s..i)
                        break
                    }
                }
            }
            text = text.replace("<sup>","")
            text = text.replace("</sup>","")
            text = text.replace("</a>", " ")
            repeat(5) {
                text = text.removePrefix("\n")
            }
            val spaces = mutableListOf<Pair<Int, Int>>()
            var k = 0
            while (k<text.length) {
                if (text[k] == ' ' || text[k] == '\n' || text[k] =='n') {
                    for (j in k until text.length) {
                        if (text[j] != ' ' && text[j] != '\n' && text[j] !='n') {
                            if (j-1!=k) {
                                spaces.add(k to (j - 1))
                                k = j
                            }
                            break
                        }
                    }
                }
                k++
            }
            var r = 0
            spaces.forEach { p ->
                var isSpaceNeeded = false
                for (i in p.first-r..p.second-r) {
                    if (text[i]=='\n')
                        isSpaceNeeded = true
                }
                if (isSpaceNeeded) {
                    text = text.replaceRange(p.first - r..p.second - r, "\n    ")
                    r+= p.second-p.first-4
                }
                else {
                    text = text.removeRange(p.first - r, p.second - r)
                    r += p.second - p.first
                }
            }
            for (i in text.length-1 downTo 0) {
                if (text[i] != ' ' && text[i] != '\n' && text[i] != 'n') {
                    text = text.removeRange(i + 1, text.length - 1)
                    break
                }
            }
            for (i in text.indices) {
                if (text[i] != ' ' && text[i] != '\n' && text[i] != 'n') {
                    text = text.removeRange(0, i - 1)
                    break
                }
            }
            val attrs = mutableListOf<Pair<Int, Int>>()
            var s = 0
            for (i in text.indices) {
                if (text[i]=='<')
                    s = i
                if (text[i]=='>')
                    attrs.add(s to i+1)
            }
            r = 0
            attrs.forEach { p ->
                text = text.removeRange(p.first - r, p.second - r)
                r += p.second - p.first
            }
            if (text.isBlank())
                text = ""
            val description = text
            try {
                val imagesContainer = div[0].getElementsByTag("img")
                if (imagesContainer.isEmpty())
                    news.add(DataNews(url = newsUrl, images = null, title = header, description = description))
                else {
                    val images = mutableListOf<String>()
                    for (i in imagesContainer.indices) {
                        val src = imagesContainer[i].absUrl("src")
                        if (!src.contains("https://vk.com/emoji")) {
                            images.add(src)
                            isImageContains = true
                            break
                        }
                    }
                    if (!isImageContains)
                        news.add(DataNews(url = newsUrl, images = null, title = header, description = description))
                    else
                        news.add(DataNews(url = newsUrl, images = images, title = header, description = description))
                }
            } catch (e: Exception) {
                Log.e("FETCH_NEWS", e.message.toString())
            }
            divText = divText.replaceRange(changingNumIndex..changingNumIndex, "@")
            num++
        }
        return@withContext news
    }


    private fun parseNewsPage(doc: Document): DataNews {
        var isImageContains = false
        val news = mutableListOf<DataNews>()
        val headerDiv = doc.select("div[class=item-page]")
        val header = headerDiv.select("h2").first().text()
        val mainDiv = doc.select("div[itemprop=articleBody]")
        var text = mainDiv.html()
        while ('<' in text) {
            val i = text.indexOf('<')
            val j = text.indexOf('>')
            text =
                if (text[i+1] == 'b' && text[j-1] == 'r')
                    text.replaceRange(i..j, "_@#!@$^!!_")
                else
                    text.removeRange(i, j+1)
        }
        text = text.replace("&nbsp;"," ")
        text = text.replace("_@#!@$^!!_", "\n")
        text = text.replace("  ", " ")
        text = text.replace(" \n", "\n")
        text = text.replace("\n ", "\n")
        while ("\n\n" in text)
            text = text.replace("\n\n", "\n")

        if (text.isBlank())
            text = ""
        val description = text
        try {
            val imagesContainer = headerDiv[0].getElementsByTag("img")
            if (imagesContainer.isEmpty())
                news.add(DataNews(url = "", images = null, title = header, description = description))
            else {
                val images = mutableListOf<String>()
                imagesContainer.forEach {
                    val src = it.absUrl("src")
                    if (!src.contains("https://vk.com/emoji")) {
                        images.add(src)
                        isImageContains = true
                    }
                }
                if (!isImageContains)
                    news.add(DataNews(url = "", images = null, title = header, description = description))
                else
                    news.add(DataNews(url = "", images = images, title = header, description = description))
            }
        } catch (e: Exception) {
            Log.e("FETCH_NEWS", e.message.toString())
        }
        return news[0]

    }


    companion object {
        const val BASE_URL = "http://lyceum8.perm.ru/"
        const val PAGE_SIZE = 12
    }

}