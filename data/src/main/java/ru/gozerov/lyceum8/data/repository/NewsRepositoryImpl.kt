package ru.gozerov.lyceum8.data.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.domain.entity.news.DomainNews
import ru.gozerov.domain.repository.NewsRepository
import ru.gozerov.lyceum8.data.cache.paging.NewsPagingSource
import ru.gozerov.lyceum8.data.cache.room.RoomNewsRepository
import ru.gozerov.lyceum8.data.rest.NewsSource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val roomNewsRepository: RoomNewsRepository,
    private val newsSource: NewsSource
): NewsRepository {

    override suspend fun getNewsById(newsUrl: String): DomainNews = withContext(Dispatchers.IO) {
        val dataNews = newsSource.getNewsByUrl(newsUrl)
        return@withContext DomainNews(dataNews.id, dataNews.url, dataNews.images, dataNews.title, dataNews.description)
    }

    override fun getRecentNews(): PagingSource<Int, DomainNews> {
        return NewsPagingSource(newsSource)
    }

    override suspend fun initNews(onUpdateEnded: () -> Unit) {
        val news = newsSource.getNewsByPage(0)
        roomNewsRepository.saveNews(news)
        onUpdateEnded()
    }

}