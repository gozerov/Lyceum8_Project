package ru.gozerov.lyceum8.screens.selected_news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Scale
import kotlinx.coroutines.Dispatchers
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.databinding.FragmentSelectedNewsBinding
import ru.gozerov.lyceum8.screens.gallery.GalleryFragment.Companion.ARG_CURRENT_ITEM
import ru.gozerov.lyceum8.screens.gallery.GalleryFragment.Companion.ARG_IMAGES
import ru.gozerov.lyceum8.screens.news_list.models.News
import ru.gozerov.lyceum8.screens.selected_news.adapter.NewsImagesAdapter
import ru.gozerov.lyceum8.screens.selected_news.states.SelectedNewsIntent
import ru.gozerov.lyceum8.screens.selected_news.states.SelectedNewsViewState
import ru.gozerov.lyceum8.utils.OnStatusBarUpdate
import ru.gozerov.lyceum8.utils.StatusBarStyle
import ru.gozerov.lyceum8.utils.appComponent
import ru.gozerov.lyceum8.utils.startRotateAnimation
import javax.inject.Inject

class SelectedNewsFragment : Fragment(R.layout.fragment_selected_news) {

    private lateinit var binding: FragmentSelectedNewsBinding

    private val news: News by lazy { arguments?.getParcelable<News>(ARG_NEWS) ?: throw NullPointerException("News is null")}

    private val viewModel: SelectedNewsViewModel by viewModels { factory.create(news.url) }

    @Inject
    lateinit var factory: SelectedNewsViewModel.SelectedNewsVMFactory.Factory

    private val imagesAdapter = NewsImagesAdapter {
        news.images?.let {
            findNavController().navigate(
                R.id.action_selectedNewsFragment_to_galleryFragment,
                bundleOf(
                    ARG_IMAGES to viewModel.images.toTypedArray(),
                    ARG_CURRENT_ITEM to binding.newsImagesViewPager.currentItem
                )
            )
        }
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
        binding = FragmentSelectedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.errorContainer.tryAgainButton.setOnClickListener {
            binding.errorContainer.container.visibility = View.GONE
            binding.newsContainer.visibility = View.VISIBLE
            viewModel.handleIntent(SelectedNewsIntent.LoadNews)
        }
        (requireActivity() as OnStatusBarUpdate).update(StatusBarStyle.DEFAULT)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.handleIntent(SelectedNewsIntent.LoadNews)
            viewModel.viewState.collect { state ->
                when(state) {
                    is SelectedNewsViewState.SuccessLoadingViewState -> {
                        val news = state.news
                        binding.txtTitleOfNews.text = news.title
                        binding.txtDescriptionOfNews.text = news.description
                        news.images?.let { images ->
                            if (images.size>1) {
                                imagesAdapter.data = images
                                binding.newsImagesViewPager.adapter = imagesAdapter
                                binding.newsImagesViewPager.visibility = View.VISIBLE
                                binding.txtPhotos.visibility = View.VISIBLE
                                binding.newsImagesViewPager.setCurrentItem(viewModel.currentItem, false)
                            }
                            else if (images.size==1){
                                binding.imgNews.load(images[0]) {
                                    this
                                        .scale(Scale.FILL)
                                        .error(R.color.grey_200)
                                        .dispatcher(Dispatchers.IO)
                                }
                                binding.imgNews.visibility = View.VISIBLE
                                binding.txtPhotos.visibility = View.VISIBLE
                                binding.imgNews.setOnClickListener {
                                    findNavController().navigate(
                                        R.id.action_selectedNewsFragment_to_galleryFragment,
                                        bundleOf(
                                            ARG_IMAGES to images.toTypedArray(),
                                            ARG_CURRENT_ITEM to 0
                                        )
                                    )
                                }
                            }
                            else
                                binding.txtPhotos.visibility = View.GONE
                        }
                        binding.loadingIndicator.visibility = View.GONE
                        binding.txtTitleOfNews.visibility = View.VISIBLE
                        binding.txtDescriptionOfNews.visibility = View.VISIBLE
                            binding.txtTitleOfNews.post {
                                binding.scrollContainer.scrollY = viewModel.scrollPosition
                            }

                    }
                    is SelectedNewsViewState.ErrorViewState -> {
                        binding.newsContainer.visibility = View.GONE
                        binding.errorContainer.container.visibility = View.VISIBLE
                        binding.errorContainer.imgGear.startRotateAnimation()
                    }
                }
            }
        }

    }

    override fun onPause() {
        viewModel.handleIntent(SelectedNewsIntent.SaveScrollPosition(binding.scrollContainer.scrollY))
        viewModel.currentItem = binding.newsImagesViewPager.currentItem
        super.onPause()
    }

    companion object {
        const val ARG_NEWS = "ARG_NEWS"
    }

}