package ru.gozerov.lyceum8_project.data.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.lyceum8_project.data.cache.entity.RoomNewsTableConstants.NewsTable

@Entity(tableName = NewsTable.TABLE_NAME)
data class DBNews(
    @[PrimaryKey ColumnInfo(name = NewsTable.COLUMN_ID)]
    val id: Long,

    @ColumnInfo(name = NewsTable.COLUMN_IMAGE_URL)
    val imageUrl: String,

    @ColumnInfo(name = NewsTable.COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = NewsTable.COLUMN_DESCRIPTION)
    val description: String

)