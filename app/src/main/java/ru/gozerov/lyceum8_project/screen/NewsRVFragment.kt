package ru.gozerov.lyceum8_project.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.gozerov.lyceum8_project.R
import ru.gozerov.lyceum8_project.databinding.FragmentNewsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.gozerov.core.fragment.BaseFragment
import ru.gozerov.domain.entity.result.SuccessResult
import ru.gozerov.domain.entity.result.takeSuccess
import ru.gozerov.lyceum8_project.adapter.BaseAdapter
import ru.gozerov.lyceum8_project.factory.NewsRViewVMFactory
import ru.gozerov.lyceum8_project.singleton.appComponent
import ru.gozerov.lyceum8_project.viewmodel.NewsRVViewModel
import javax.inject.Inject

class NewsRVFragment : BaseFragment(R.layout.fragment_news) {

    override val viewModel by viewModels<NewsRVViewModel> {
        factory.create(this, bundleOf(ARG_NEWS_LIST to args.newsList.data))
    }

    @Inject
    lateinit var factory: NewsRViewVMFactory.Factory

    private val args: NewsRVFragmentArgs by navArgs()

    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: BaseAdapter

    override fun onAttach(context: Context) {
        context.applicationContext.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        adapter = BaseAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            launch {
                viewModel.newsList.collect { result ->
                    renderSimpleResult(binding.root, SuccessResult(result)) { newsList ->
                        newsList.takeSuccess()?.let { adapter.newsList = it }
                    }
                }
            }
            launch {
                adapter.elementFlow.collect {
                    val direction = NewsRVFragmentDirections.actionNewsRVFragment2ToSelectableNewsFragment(it.id)
                    findNavController().navigate(direction)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.newsRv.adapter = adapter
        binding.newsRv.layoutManager = LinearLayoutManager(this.requireContext())
    }

    companion object {
        const val ARG_NEWS_LIST = "newsList"
    }

}