package ru.gozerov.domain.repository

import androidx.paging.PagingSource
import ru.gozerov.domain.entity.news.DomainNews

interface NewsRepository: Repository {

    suspend fun getNewsById(newsUrl: String) : DomainNews

    fun getRecentNews() : PagingSource<Int, DomainNews>

    suspend fun initNews(onUpdateEnded: () -> Unit)


}