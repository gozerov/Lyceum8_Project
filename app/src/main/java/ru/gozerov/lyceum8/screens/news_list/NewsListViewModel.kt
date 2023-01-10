package ru.gozerov.lyceum8.screens.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.gozerov.domain.entity.news.DomainNews
import ru.gozerov.domain.usecase.GetRecentNewsUseCase
import ru.gozerov.lyceum8.screens.news_list.models.News
import ru.gozerov.lyceum8.screens.news_list.state_machine.NewsListViewState
import ru.gozerov.lyceum8.screens.news_list.state_machine.NewsListViewState.ShowingLoadedNews
import javax.inject.Inject

@Suppress("OPT_IN_IS_NOT_ENABLED")
class NewsListViewModel(
    private val getRecentNewsUseCase: GetRecentNewsUseCase,
) : ViewModel() {

    private val _viewState = MutableSharedFlow<NewsListViewState>(1, 0, BufferOverflow.DROP_OLDEST)
    val viewState: SharedFlow<NewsListViewState> = _viewState.asSharedFlow()

    private var news: StateFlow<PagingData<News>?> = MutableStateFlow("").toPagingData()

    init {
        viewModelScope.launch {
            collectNews()
        }
    }

    suspend fun refreshNewsPager() {
        news = MutableStateFlow("").toPagingData()
        collectNews()
    }

    private suspend fun collectNews() {
        news.collect { data ->
            data?.let {
                _viewState.emit(ShowingLoadedNews(it))
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun newPager(query: String): Pager<Int, DomainNews>? {
        val pagingSource = try {
            getRecentNewsUseCase.execute()
        } catch (e: Exception) {
            null
        }
        return pagingSource?.let {
            return Pager(
                config = PagingConfig(12, enablePlaceholders = false, initialLoadSize = 12),
                initialKey = null
            ) { getRecentNewsUseCase.execute() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun MutableStateFlow<String>.toPagingData(): StateFlow<PagingData<News>?> =
        this.map(::newPager)
            .flatMapLatest { pager ->
                pager?.flow ?: flowOf<PagingData<DomainNews>>(PagingData.empty()).apply {
                    _viewState.tryEmit(NewsListViewState.ErrorNewsListViewState)
                }

            }
            .map { pager ->
                pager.map {
                    News(
                        it.id,
                        it.url,
                        it.imageUrl,
                        it.title,
                        it.description
                    )
                }
            }
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    class NewsListViewModelFactory @Inject constructor(
        private val getRecentNewsUseCase: GetRecentNewsUseCase,
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsListViewModel(getRecentNewsUseCase) as T
        }
    }

}