package ru.gozerov.lyceum8_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.gozerov.domain.entity.news.News
import ru.gozerov.lyceum8_project.R
import ru.gozerov.lyceum8_project.databinding.NewsRvItemBinding

class BaseAdapter : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>(), View.OnClickListener {

    private val _elementFlow = MutableSharedFlow<News>(0,1,BufferOverflow.DROP_OLDEST)
    val elementFlow = _elementFlow.asSharedFlow()

    var newsList: List<News> = emptyList()
        set(value) {
            val diffUtilCallback =
                StudentDiffCallback(
                    field,
                    value
                )
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    class BaseViewHolder(
        val binding: NewsRvItemBinding
        ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsRvItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return BaseViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val news = newsList[position]
        with(holder.binding) {
            holder.itemView.tag = news
            titleRv.text = news.title
            imageRv.load(news.imageUrl) {
                this
                    .scale(Scale.FIT)
                    .error(R.drawable.test_photo)
                    .dispatcher(Dispatchers.IO)
            }
        }
    }

    override fun getItemCount(): Int = newsList.size

    override fun onClick(view: View?) {
        val news = view?.tag!! as News
        _elementFlow.tryEmit(news)
    }

}