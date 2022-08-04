package ru.gozerov.lyceum8_project.data.cache.room

import kotlinx.coroutines.flow.Flow
import ru.gozerov.lyceum8_project.data.cache.entity.DataNews
import ru.gozerov.lyceum8_project.data.cache.entity.DataNewsId

interface RoomNewsRepository {

    suspend fun getRecentNews() : List<DataNews>

    suspend fun getNewsById(dataNewsId: DataNewsId) : DataNews

    suspend fun firstInitialization()

}