package ru.gozerov.lyceum8.data.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.lyceum8.data.cache.entity.RoomNewsTableConstants.NewsTable

@Entity(tableName = NewsTable.TABLE_NAME)
data class DBNews(
    @[PrimaryKey(autoGenerate = true) ColumnInfo(name = NewsTable.COLUMN_ID)]
    val id: Int = 0,

    @ColumnInfo(name = NewsTable.COLUMN_URL)
    val url: String,

    @ColumnInfo(name = NewsTable.COLUMN_IMAGES)
    val images: String?,

    @ColumnInfo(name = NewsTable.COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = NewsTable.COLUMN_DESCRIPTION)
    val description: String

)