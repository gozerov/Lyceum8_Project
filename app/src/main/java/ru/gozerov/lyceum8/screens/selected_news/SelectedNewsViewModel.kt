package ru.gozerov.lyceum8.screens.selected_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecase.GetNewsByIdUseCase
import ru.gozerov.lyceum8.screens.news_list.models.News
import ru.gozerov.lyceum8.screens.selected_news.states.SelectedNewsIntent
import ru.gozerov.lyceum8.screens.selected_news.states.SelectedNewsViewState

class SelectedNewsViewModel(
    private val newsUrl: String,
    private val getNewsByIdUseCase: GetNewsByIdUseCase
) : ViewModel() {

    private val _viewState: MutableSharedFlow<SelectedNewsViewState> = MutableSharedFlow(1, 0, BufferOverflow.DROP_OLDEST)
    val viewState: SharedFlow<SelectedNewsViewState> = _viewState.asSharedFlow()

    var images = listOf<String>()
    private set

    var scrollPosition = 0
    private set

    var currentItem = 0
    set(value) {
        if (value >= 0)
            field = value
    }

    fun handleIntent(intent: SelectedNewsIntent) {
        viewModelScope.launch {
            when(intent) {
                is SelectedNewsIntent.LoadNews -> loadNews()
                is SelectedNewsIntent.SaveScrollPosition -> {
                    scrollPosition = intent.position
                }
            }
        }

    }


   private suspend fun loadNews() {
        try {
            val domainNews = getNewsByIdUseCase.execute(newsUrl)
            domainNews.imageUrl?.let {
                images = it
            }
            _viewState.emit(
                SelectedNewsViewState.SuccessLoadingViewState(
                    news = News(
                        id = domainNews.id,
                        url = domainNews.url,
                        images = domainNews.imageUrl,
                        title = domainNews.title,
                        description = domainNews.description
                    )
                )
            )
        } catch (e: Exception) {
            _viewState.tryEmit(SelectedNewsViewState.ErrorViewState)
        }
    }

    class SelectedNewsVMFactory @AssistedInject constructor(
        @Assisted("news") private val newsUrl: String,
        private val getNewsByIdUseCase: GetNewsByIdUseCase
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SelectedNewsViewModel(newsUrl, getNewsByIdUseCase) as T
        }

        @AssistedFactory
        interface Factory {

            fun create(@Assisted("news") newsUrl: String): SelectedNewsVMFactory

        }

    }
}
