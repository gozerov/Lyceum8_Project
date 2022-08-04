package ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine

import ru.gozerov.domain.entity.news.News

sealed class NewsListViewState {
    object Loading: NewsListViewState()

    data class ShowingData(
        val data: List<News>
    ): NewsListViewState()

    data class Error(
        val message: String
    ): NewsListViewState()
}