package ru.gozerov.lyceum8_project.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.gozerov.core.viewmodel.BaseViewModel
import ru.gozerov.domain.entity.news.News
import ru.gozerov.domain.entity.result.PendingResult
import ru.gozerov.domain.entity.result.Result
import ru.gozerov.domain.entity.result.SuccessResult
import ru.gozerov.domain.usecase.GetRecentNewsUseCase

class NewsRVViewModel(
    private val getRecentNewsUseCase: GetRecentNewsUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _newsList = MutableStateFlow<Result<List<News>>>(PendingResult())
    val newsList: StateFlow<Result<List<News>>> = _newsList.asStateFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        val handleNewsList = savedStateHandle.get<List<News>>("newsList")
        getRecentNewsUseCase.execute().collect { newsList ->
            if (handleNewsList != newsList)
                _newsList.tryEmit(SuccessResult(newsList))
            else
                _newsList.emit(SuccessResult(handleNewsList))
        }
    }

}