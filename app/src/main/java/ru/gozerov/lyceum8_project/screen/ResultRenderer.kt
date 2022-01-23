package ru.gozerov.lyceum8_project.screen

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import ru.gozerov.lyceum8_project.R
import ru.gozerov.lyceum8_project.databinding.PartResultBinding
import ru.gozerov.core.fragment.BaseFragment
import ru.gozerov.domain.entity.result.Result

fun <T> BaseFragment.renderSimpleResult(root: ViewGroup, result: Result<T>, onSuccess: (T) -> Unit) {
    val binding = PartResultBinding.bind(root)
    renderResult(
        root = root,
        result = result,
        onPending = {
            binding.progressBar.visibility = View.VISIBLE
        },
        onError = {
            binding.errorContainer.visibility = View.VISIBLE
        },
        onSuccess = { successData ->
            root.children
                .filter { it.id != R.id.progressBar && it.id != R.id.errorContainer }
                .forEach { it.visibility = View.VISIBLE }
            onSuccess(successData)
        }
    )
}
fun BaseFragment.onTryAgain(root: ViewGroup, onTryAgainPressed: () -> Unit) {
    root.findViewById<Button>(R.id.tryAgainButton).setOnClickListener { onTryAgainPressed() }
}