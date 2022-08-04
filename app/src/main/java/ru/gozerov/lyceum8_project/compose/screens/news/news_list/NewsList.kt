package ru.gozerov.lyceum8_project.compose.screens.news.news_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.gozerov.domain.entity.news.News

@Parcelize
data class NewsList(
    val data: List<News>
): Parcelable
