package ru.gozerov.lyceum8_project.factory

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.gozerov.domain.usecase.GetRecentNewsUseCase
import ru.gozerov.lyceum8_project.viewmodel.NewsRVViewModel

class NewsRViewVMFactory  @AssistedInject constructor(
    private val getRecentNewsUseCase: GetRecentNewsUseCase,
    @Assisted("owner") owner: SavedStateRegistryOwner,
    @Assisted("defaultArgs") defaultArgs: Bundle?
    ): AbstractSavedStateViewModelFactory(owner, defaultArgs) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return NewsRVViewModel(getRecentNewsUseCase, handle) as T
        }

        @AssistedFactory
        interface Factory {

            fun create(
                @Assisted("owner") owner: SavedStateRegistryOwner,
                @Assisted("defaultArgs") defaultArgs: Bundle?
            ): NewsRViewVMFactory

        }
}