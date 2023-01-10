package ru.gozerov.lyceum8.screens.selected_news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import kotlinx.coroutines.Dispatchers
import ru.gozerov.lyceum8.screens.selected_news.adapter.NewsImagesAdapter.NewsImagesViewHolder
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.databinding.ItemImageViewPagerBinding

class NewsImagesAdapter(
    private val onClick: () -> Unit
): RecyclerView.Adapter<NewsImagesViewHolder>(), View.OnClickListener {

    inner class NewsImagesViewHolder(val imageView: ImageView): RecyclerView.ViewHolder(imageView)

    var data: List<String> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageViewPagerBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return NewsImagesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NewsImagesViewHolder, position: Int) {
        val imageUrl = data[position]
        holder.imageView.load(imageUrl) {
            this
                .scale(Scale.FILL)
                .error(R.color.grey_200)
                .dispatcher(Dispatchers.IO)
        }
    }

    override fun onClick(p0: View?) = onClick.invoke()


}