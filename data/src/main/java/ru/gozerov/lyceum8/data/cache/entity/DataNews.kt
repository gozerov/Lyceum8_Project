package ru.gozerov.lyceum8.data.cache.entity

data class DataNews(
    val id: Int = 0,
    val url: String,
    val images: List<String>?,
    val title: String,
    val description: String,
)