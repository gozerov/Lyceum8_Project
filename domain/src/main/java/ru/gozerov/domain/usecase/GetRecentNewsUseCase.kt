package ru.gozerov.domain.usecase

import androidx.paging.PagingSource
import ru.gozerov.domain.entity.news.DomainNews
import ru.gozerov.domain.repository.NewsRepository
import javax.inject.Inject

class GetRecentNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    fun execute(): PagingSource<Int, DomainNews> {
        return newsRepository.getRecentNews()
    }

}