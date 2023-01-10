package ru.gozerov.lyceum8.data.cache.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.gozerov.domain.entity.news.DomainNews
import ru.gozerov.lyceum8.data.cache.entity.DataNews
import ru.gozerov.lyceum8.data.rest.NewsSource
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val newsSource: NewsSource
): PagingSource<Int, DomainNews>() {

    override fun getRefreshKey(state: PagingState<Int, DomainNews>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainNews> {
        return try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val response = newsSource.getNewsByPage(pageNumber)
            val nextPageNumber = if (response.isEmpty()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            LoadResult.Page(response.map { it.toDomainNews() }, prevPageNumber, nextPageNumber)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 0
    }

    private fun DataNews.toDomainNews() = DomainNews(id, url, images, title, description)

}