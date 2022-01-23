package ru.gozerov.lyceum8_project.viewmodel

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onSubscription
import ru.gozerov.core.viewmodel.BaseViewModel
import ru.gozerov.domain.entity.news.News
import ru.gozerov.domain.entity.news.NewsId
import ru.gozerov.domain.usecase.GetNewsByIdUseCase

class SelectableNewsViewModel(
    private val newsId: Long,
    private val getNewsByIdUseCase: GetNewsByIdUseCase
) : BaseViewModel() {

    private val _currentNewsFlow = MutableSharedFlow<News>(0,1 ,BufferOverflow.DROP_OLDEST)
    val currentNewsFlow = _currentNewsFlow.asSharedFlow().onSubscription {
        _currentNewsFlow.tryEmit(getNewsByIdUseCase.execute(NewsId(newsId)))
    }

}