package ru.gozerov.lyceum8_project.compose.screens.splash.state_machine

import ru.gozerov.lyceum8_project.compose.screens.news.news_list.NewsList

sealed class SplashViewState {
    object Loading: SplashViewState()

    data class SuccessLoading(
        val newsList: NewsList
    ): SplashViewState()

    data class ErrorLoading(
        val message: String
    ): SplashViewState()
}
