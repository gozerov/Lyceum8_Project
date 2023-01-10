package ru.gozerov.lyceum8.screens.gallery.adapter.galleryAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import kotlinx.coroutines.Dispatchers
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.databinding.ItemImageZoomableBinding

class GalleryAdapter: RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(val imageView: ImageView): RecyclerView.ViewHolder(imageView)

    var data: List<String> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageZoomableBinding.inflate(inflater, parent, false)
        return GalleryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val imageUrl = data[position]
        holder.imageView.load(imageUrl) {
            this
                .scale(Scale.FILL)
                .error(R.color.black)
                .dispatcher(Dispatchers.IO)
        }

    }

}