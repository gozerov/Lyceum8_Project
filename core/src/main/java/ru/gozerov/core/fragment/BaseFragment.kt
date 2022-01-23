package ru.gozerov.core.fragment

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.Fragment
import ru.gozerov.core.viewmodel.BaseViewModel
import ru.gozerov.domain.entity.result.ErrorResult
import ru.gozerov.domain.entity.result.PendingResult
import ru.gozerov.domain.entity.result.Result
import ru.gozerov.domain.entity.result.SuccessResult

abstract class BaseFragment(@LayoutRes fragmentId: Int): Fragment(fragmentId) {
    abstract val viewModel: BaseViewModel

    fun <T> renderResult(root: ViewGroup, result: Result<T>,
                         onPending: () -> Unit,
                         onError: (Exception) -> Unit,
                         onSuccess: (T) -> Unit) {
        root.children.forEach { it.visibility = View.GONE }
        when(result) {
            is PendingResult -> onPending()
            is ErrorResult -> onError(result.exception)
            is SuccessResult -> onSuccess(result.data)
        }
    }

}