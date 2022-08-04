package ru.gozerov.lyceum8_project.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.gozerov.domain.usecase.GetNewsByIdUseCase
import ru.gozerov.lyceum8_project.viewmodel.SelectableNewsViewModel

/*
class SelectableNewsVMFactory @AssistedInject constructor(
    @Assisted("newsId") private val newsId: Long,
    private val getNewsByIdUseCase: GetNewsByIdUseCase

): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SelectableNewsViewModel(newsId, getNewsByIdUseCase) as T
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("newsId") newsId: Long): SelectableNewsVMFactory

    }

}*/
