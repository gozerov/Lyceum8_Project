package ru.gozerov.lyceum8.screens.selected_news.states

import ru.gozerov.lyceum8.screens.news_list.models.News

sealed class SelectedNewsViewState {

    data class SuccessLoadingViewState(
        val news: News
    ): SelectedNewsViewState()

    object ErrorViewState: SelectedNewsViewState()

}