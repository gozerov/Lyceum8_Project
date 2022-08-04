package ru.gozerov.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.entity.news.News
import ru.gozerov.domain.repository.NewsRepository
import javax.inject.Inject

class GetRecentNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun execute(): List<News> {
        newsRepository.firstInitialization()
        return newsRepository.getRecentNews()
    }

}