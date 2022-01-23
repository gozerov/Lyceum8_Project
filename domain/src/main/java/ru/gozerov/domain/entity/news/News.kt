package ru.gozerov.domain.entity.news

import java.io.Serializable

class News(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val description: String
): Serializable