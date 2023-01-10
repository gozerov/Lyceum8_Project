package ru.gozerov.lyceum8.data.rest

import ru.gozerov.lyceum8.data.cache.entity.DataNews

interface NewsSource {

    suspend fun getNewsByPage(page: Int): List<DataNews>

    suspend fun getNewsByUrl(url: String): DataNews

}