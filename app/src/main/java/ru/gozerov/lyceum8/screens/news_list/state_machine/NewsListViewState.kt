package ru.gozerov.lyceum8.screens.news_list.state_machine

import androidx.paging.PagingData
import ru.gozerov.lyceum8.screens.news_list.models.News

sealed class NewsListViewState {

    object DefaultNewsListViewState: NewsListViewState()

    data class ShowingLoadedNews(
        val data: PagingData<News>
    ): NewsListViewState()

    object ErrorNewsListViewState: NewsListViewState()

    object SetRefreshingFalse: NewsListViewState()

}
