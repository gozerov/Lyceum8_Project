package ru.gozerov.lyceum8.screens.news_list.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsList(
    val value: List<News>
): Parcelable