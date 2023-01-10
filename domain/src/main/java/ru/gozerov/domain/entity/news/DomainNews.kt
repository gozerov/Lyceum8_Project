package ru.gozerov.domain.entity.news

import java.io.Serializable

class DomainNews(
    val id: Int,
    val url: String,
    val imageUrl: List<String>?,
    val title: String,
    val description: String
): Serializable