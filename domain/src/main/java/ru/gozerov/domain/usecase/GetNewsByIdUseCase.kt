package ru.gozerov.domain.usecase

import ru.gozerov.domain.entity.news.DomainNews
import ru.gozerov.domain.entity.news.NewsId
import ru.gozerov.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsByIdUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun execute(newsUrl: String): DomainNews {
        return newsRepository.getNewsById(newsUrl)
    }

}