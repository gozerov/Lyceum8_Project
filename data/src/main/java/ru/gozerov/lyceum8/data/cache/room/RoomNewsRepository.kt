package ru.gozerov.lyceum8.data.cache.room

import kotlinx.coroutines.flow.Flow
import ru.gozerov.lyceum8.data.cache.entity.DataNews

interface RoomNewsRepository {

    suspend fun getRecentNews() : Flow<List<DataNews>>

    suspend fun saveNews(news: List<DataNews>)

}