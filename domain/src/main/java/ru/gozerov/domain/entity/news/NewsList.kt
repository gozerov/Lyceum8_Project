package ru.gozerov.domain.entity.news

import java.io.Serializable

data class NewsList(
    val data: List<News>
): Serializable