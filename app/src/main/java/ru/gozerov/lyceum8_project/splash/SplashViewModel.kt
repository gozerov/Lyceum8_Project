package ru.gozerov.lyceum8_project.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.core.viewmodel.BaseViewModel
import ru.gozerov.domain.usecase.GetRecentNewsUseCase
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.NewsList
import ru.gozerov.lyceum8_project.compose.screens.splash.state_machine.SplashIntent
import ru.gozerov.lyceum8_project.compose.screens.splash.state_machine.SplashViewState
import ru.gozerov.lyceum8_project.compose.screens.splash.state_machine.SplashViewState.Loading
import ru.gozerov.lyceum8_project.compose.screens.splash.state_machine.SplashViewState.SuccessLoading
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor (
    private val getRecentNewsUseCase: GetRecentNewsUseCase

) : BaseViewModel() {

    private val _viewState = MutableStateFlow<SplashViewState>(Loading)
    val viewState: StateFlow<SplashViewState> = _viewState.asStateFlow()

    fun handleIntent(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.EnterScreen -> {
                viewModelScope.launch {
                    val news = getRecentNewsUseCase.execute()
                    delay(1000)
                    _viewState.emit(SuccessLoading(NewsList(news)))
                }
            }
        }
    }


}