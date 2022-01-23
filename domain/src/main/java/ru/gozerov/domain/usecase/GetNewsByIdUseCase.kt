package ru.gozerov.domain.usecase

import ru.gozerov.domain.entity.news.News
import ru.gozerov.domain.entity.news.NewsId
import ru.gozerov.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsByIdUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun execute(newsId: NewsId): News {
        return newsRepository.getNewsById(newsId)
    }

}