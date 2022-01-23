package ru.gozerov.lyceum8_project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gozerov.domain.entity.news.News
import ru.gozerov.domain.entity.news.NewsId
import ru.gozerov.domain.repository.NewsRepository
import ru.gozerov.lyceum8_project.data.cache.entity.DataNews
import ru.gozerov.lyceum8_project.data.cache.entity.DataNewsId
import ru.gozerov.lyceum8_project.data.cache.room.RoomNewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val roomNewsRepository: RoomNewsRepository
): NewsRepository {

    override suspend fun getNewsById(newsId: NewsId): News {
        return roomNewsRepository.getNewsById(newsId.toDataNewsId()).toNews()
    }

    override suspend fun firstInitialization() {
        roomNewsRepository.firstInitialization()
    }

    override suspend fun getRecentNews(): Flow<List<News>> {
        return roomNewsRepository.getRecentNews().toNewsListFlow()
    }

    private fun NewsId.toDataNewsId() = DataNewsId(id)

    private fun DataNews.toNews() = News(id, imageUrl, title, description)
    private fun List<DataNews>.toNewsList() = map { it.toNews() }
    private fun Flow<List<DataNews>>.toNewsListFlow() = map { it.toNewsList() }

}