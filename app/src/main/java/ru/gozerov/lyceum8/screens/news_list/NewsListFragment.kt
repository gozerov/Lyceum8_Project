package ru.gozerov.lyceum8.screens.news_list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.databinding.FragmentNewsBinding
import ru.gozerov.lyceum8.screens.news_list.NewsListViewModel.NewsListViewModelFactory
import ru.gozerov.lyceum8.screens.news_list.adapter.NewsListAdapter
import ru.gozerov.lyceum8.screens.news_list.adapter.NewsLoaderStateAdapter
import ru.gozerov.lyceum8.screens.news_list.state_machine.NewsListViewState.*
import ru.gozerov.lyceum8.screens.selected_news.SelectedNewsFragment.Companion.ARG_NEWS
import ru.gozerov.lyceum8.utils.appComponent
import ru.gozerov.lyceum8.utils.startRotateAnimation
import javax.inject.Inject

class NewsListFragment : Fragment(R.layout.fragment_news) {

    private val viewModel by viewModels<NewsListViewModel> { factory }

    @Inject
    lateinit var factory: NewsListViewModelFactory

    private lateinit var binding: FragmentNewsBinding

    private val handler = Handler(Looper.getMainLooper())

    private val newsAdapter = NewsListAdapter { news ->
        findNavController().navigate(
            R.id.action_newsListFragment_to_selectedNewsFragment,
            bundleOf(ARG_NEWS to news)
        )
    }

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding.toolbar.visibility == View.INVISIBLE) {
            binding.toolbar.visibility = View.VISIBLE
            binding.toolbar.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.anim_slide_down))
        }
        binding.pullToRefreshLayout.setColorSchemeColors(resources.getColor(R.color.deep_orange_600, context?.theme))
        binding.pullToRefreshLayout.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.white, context?.theme))
        binding.newsRv.adapter = newsAdapter.withLoadStateFooter(NewsLoaderStateAdapter())
        binding.newsRv.layoutManager = LinearLayoutManager(this.requireContext())

        binding.pullToRefreshLayout.setOnRefreshListener {
            newsAdapter.refresh()
            if (binding.pullToRefreshLayout.isRefreshing)
                handler.postDelayed({
                    binding.pullToRefreshLayout.isRefreshing = false
                },500)
        }

        binding.errorContainer.tryAgainButton.setOnClickListener {
            binding.errorContainer.container.visibility = View.GONE
            lifecycleScope.launchWhenStarted {
                viewModel.refreshNewsPager()
                newsAdapter.refresh()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collectLatest { state ->
                    when (state) {
                        is DefaultNewsListViewState -> {}
                        is ShowingLoadedNews -> {
                            newsAdapter.submitData(state.data)
                        }
                        is SetRefreshingFalse -> {
                            if (binding.pullToRefreshLayout.isRefreshing)
                                binding.pullToRefreshLayout.isRefreshing = false
                        }
                        is ErrorNewsListViewState -> {
                            binding.errorContainer.container.visibility = View.VISIBLE
                            binding.errorContainer.imgGear.startRotateAnimation()
                        }
                    }
                }
            }
        }
    }

}