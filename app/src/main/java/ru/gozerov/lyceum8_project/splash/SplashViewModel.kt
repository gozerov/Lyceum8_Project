package ru.gozerov.lyceum8_project.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.gozerov.core.viewmodel.BaseViewModel
import ru.gozerov.domain.entity.news.News
import ru.gozerov.domain.usecase.GetRecentNewsUseCase
import javax.inject.Inject

class SplashViewModel(
    private val getRecentNewsUseCase: GetRecentNewsUseCase

) : BaseViewModel() {

    private val _event = MutableSharedFlow<List<News>>(0, 1, BufferOverflow.DROP_OLDEST)
    val event = _event.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    init {
        viewModelScope.launch {
            getRecentNewsUseCase.execute().collect { newsList ->
                _event.emit(newsList)
            }
        }
    }

    class SplashViewModelFactory @Inject constructor(
        private val getRecentNewsUseCase: GetRecentNewsUseCase
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SplashViewModel(getRecentNewsUseCase) as T
        }

    }

}