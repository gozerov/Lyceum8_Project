package ru.gozerov.domain.usecase

import ru.gozerov.domain.repository.NewsRepository
import javax.inject.Inject

class UpdateNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun execute(onUpdateEnded: () -> Unit) {
        newsRepository.initNews(onUpdateEnded)
    }

}