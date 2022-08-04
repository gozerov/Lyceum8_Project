package ru.gozerov.lyceum8_project.compose.screens.news.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecase.GetRecentNewsUseCase
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine.NewsListIntent
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine.NewsListIntent.LoadData
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine.NewsListIntent.RetryLoading
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine.NewsListViewState
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine.NewsListViewState.Loading
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine.NewsListViewState.ShowingData
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getRecentNewsUseCase: GetRecentNewsUseCase,
): ViewModel() {

    private val _viewState = MutableStateFlow<NewsListViewState>(Loading)
    val viewState: StateFlow<NewsListViewState> = _viewState.asStateFlow()

    fun handleIntent(intent: NewsListIntent) {
        when (intent)  {
            is NewsListIntent.EnterScreen -> {
                viewModelScope.launch {
                    _viewState.emit(ShowingData(intent.news.data))
                }
            }
            is LoadData -> {
                viewModelScope.launch {
                    _viewState.emit(ShowingData(getRecentNewsUseCase.execute()))
                }
            }
            is RetryLoading -> {
                viewModelScope.launch {
                    _viewState.emit(ShowingData(getRecentNewsUseCase.execute()))
                }
            }

        }
    }

}