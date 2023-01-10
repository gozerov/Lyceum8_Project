package ru.gozerov.lyceum8.screens.news_list.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val id: Int,
    val url: String,
    val images: List<String>?,
    val title: String,
    val description: String
): Parcelable