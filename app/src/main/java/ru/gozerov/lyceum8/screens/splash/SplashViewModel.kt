package ru.gozerov.lyceum8.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecase.UpdateNewsUseCase
import ru.gozerov.lyceum8.screens.splash.states.SplashViewState
import ru.gozerov.lyceum8.screens.splash.states.SplashViewState.DefaultViewState
import javax.inject.Inject

class SplashViewModel(
    private val updateNewsUseCase: UpdateNewsUseCase
) : ViewModel() {

    private val _viewState: MutableSharedFlow<SplashViewState> = MutableSharedFlow(1, 0, BufferOverflow.DROP_OLDEST)
    var viewState: SharedFlow<SplashViewState> = _viewState.asSharedFlow()

    init {
        _viewState.tryEmit(DefaultViewState)
    }

    fun updateNews(onUpdateEnded: () -> Unit) {
        viewModelScope.launch {
            try {
                updateNewsUseCase.execute(onUpdateEnded)
            } catch(e: Exception) {
                _viewState.emit(SplashViewState.ErrorViewState)
            }
        }
    }

    class SplashViewModelFactory @Inject constructor(
        private val updateNewsUseCase: UpdateNewsUseCase
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SplashViewModel(updateNewsUseCase) as T
        }

    }

}