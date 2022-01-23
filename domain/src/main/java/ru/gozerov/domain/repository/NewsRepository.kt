package ru.gozerov.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.entity.news.News
import ru.gozerov.domain.entity.news.NewsId

interface NewsRepository: Repository {

    suspend fun getNewsById(newsId: NewsId) : News

    suspend fun firstInitialization()

    suspend fun getRecentNews() : Flow<List<News>>

}