package ru.gozerov.lyceum8.screens.news_list.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.gozerov.lyceum8.screens.news_list.models.News

class NewsDiffCallback: DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem==newItem
    }
}