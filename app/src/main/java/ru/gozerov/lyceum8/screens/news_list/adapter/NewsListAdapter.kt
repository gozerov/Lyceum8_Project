package ru.gozerov.lyceum8.screens.news_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import kotlinx.coroutines.Dispatchers
import ru.gozerov.lyceum8.screens.news_list.adapter.NewsListAdapter.NewsViewHolder
import ru.gozerov.lyceum8.screens.news_list.adapter.NewsListAdapter.NewsViewHolder.NewsWithImageHolder
import ru.gozerov.lyceum8.screens.news_list.adapter.NewsListAdapter.NewsViewHolder.SimpleNewsHolder
import ru.gozerov.lyceum8.screens.news_list.models.News
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.databinding.ItemNewsImageBinding
import ru.gozerov.lyceum8.databinding.ItemNewsSimpleBinding

class NewsListAdapter(
    private val onNewsClicked: (News) -> Unit
) : PagingDataAdapter<News, NewsViewHolder>(NewsDiffCallback()), View.OnClickListener {

    sealed class NewsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        class NewsWithImageHolder(
            val binding: ItemNewsImageBinding
        ): NewsViewHolder(binding.root)

        class SimpleNewsHolder(
            val binding: ItemNewsSimpleBinding
        ): NewsViewHolder(binding.root)
    }

    override fun getItemViewType(position: Int): Int {
        val news = getItem(position)
        return if ( news?.images!= null && news.images.isNotEmpty())
            NEWS_WITH_IMAGE
        else
            SIMPLE_NEWS
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            SIMPLE_NEWS -> {
                val binding = ItemNewsSimpleBinding.inflate(inflater, parent, false)
                binding.root.setOnClickListener(this)
                SimpleNewsHolder(binding)
            }
            NEWS_WITH_IMAGE -> {
                val binding = ItemNewsImageBinding.inflate(inflater, parent, false)
                binding.root.setOnClickListener(this)
                NewsWithImageHolder(binding)
            }
            else -> {
                throw Exception("Unknown ViewHolder type")
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        when(holder) {
            is SimpleNewsHolder -> {
                with(holder.binding) {
                    holder.itemView.tag = news
                    txtNewsTitle.text = news?.title
                }
            }
            is NewsWithImageHolder -> {
                with(holder.binding) {
                    holder.itemView.tag = news
                    txtNewsTitle.text = news?.title
                    if (news?.images != null && news.images.isNotEmpty()) {
                        imgNews.load(news.images[0]) {
                            this
                                .scale(Scale.FILL)
                                .error(R.drawable.black70_background)
                                .dispatcher(Dispatchers.IO)
                        }
                    }

                }
            }
        }
    }

    override fun onClick(view: View?) {
        val news = view?.tag!! as News
        onNewsClicked(news)
    }

    companion object {
        const val SIMPLE_NEWS = 0
        const val NEWS_WITH_IMAGE = 1
    }

}