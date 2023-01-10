package ru.gozerov.lyceum8.data.cache.room

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.gozerov.domain.entity.news.ImagesStr
import ru.gozerov.lyceum8.data.cache.entity.DBNews
import ru.gozerov.lyceum8.data.cache.entity.DataNews
import javax.inject.Inject

class RoomNewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val gson: Gson
): RoomNewsRepository {
    override suspend fun getRecentNews(): Flow<List<DataNews>> = withContext(Dispatchers.IO){
        return@withContext newsDao.getAllNews().toDataNewsListFlow()
    }

    override suspend fun saveNews(news: List<DataNews>): Unit = withContext(Dispatchers.IO) {
        if (newsDao.getAllNews().firstOrNull()?.toDataNewsList() != news) {
            newsDao.clearDatabase()
            newsDao.firstInitialization(news.map { it.toDBNews() })
        }
    }

    private fun DataNews.toDBNews(): DBNews {
        val images = gson.toJson(ImagesStr(this.images), ImagesStr::class.java)
        return DBNews(id, url, images, title, description)
    }
    private fun DBNews.toDataNews(): DataNews {
        val images = gson.fromJson(this.images, ImagesStr::class.java)
        return DataNews(id, url, images.images, title, description)
    }
    private fun List<DBNews>.toDataNewsList() = map { it.toDataNews() }
    private fun Flow<List<DBNews>>.toDataNewsListFlow() = map { it.toDataNewsList() }

}