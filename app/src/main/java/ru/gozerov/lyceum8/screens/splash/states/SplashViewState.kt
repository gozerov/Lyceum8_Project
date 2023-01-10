package ru.gozerov.lyceum8.screens.splash.states

sealed class SplashViewState {

    object DefaultViewState: SplashViewState()
    object ErrorViewState: SplashViewState()

}
