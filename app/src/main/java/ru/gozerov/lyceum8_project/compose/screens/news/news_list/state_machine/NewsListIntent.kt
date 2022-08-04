package ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine

import ru.gozerov.lyceum8_project.compose.screens.news.news_list.NewsList

sealed class NewsListIntent {
    data class EnterScreen(
        val news: NewsList
    ): NewsListIntent()

    object LoadData: NewsListIntent()

    object RetryLoading: NewsListIntent()
}