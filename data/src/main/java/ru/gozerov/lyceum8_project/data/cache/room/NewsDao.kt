package ru.gozerov.lyceum8_project.data.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gozerov.lyceum8_project.data.cache.entity.DBNews

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews() : Flow<List<DBNews>>

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getNewsById(id: Long) : DBNews

    @Insert
    suspend fun firstInitialization(news: List<DBNews>)

    @Query("SELECT count(*) FROM news")
    suspend fun getNewsCount(): Int

}