package ru.gozerov.lyceum8.screens.selected_news.states

sealed class SelectedNewsIntent {

    object LoadNews: SelectedNewsIntent()
    data class SaveScrollPosition(
        val position: Int
    ): SelectedNewsIntent()

}
