package ru.gozerov.lyceum8.data.cache.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.gozerov.lyceum8.data.cache.entity.DBNews
import ru.gozerov.lyceum8.data.cache.entity.RoomNewsTableConstants.NewsTable.TABLE_NAME

@Database(entities = [DBNews::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object {
        private var database: NewsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NewsDatabase {
            return database ?: Room.databaseBuilder(context, NewsDatabase::class.java, TABLE_NAME).build()
            /*return if (database == null) {
                DataComponentObject.initDataComponent()
                Room.databaseBuilder(context, NewsDatabase::class.java, TABLE_NAME).build()
            } else
                database as NewsDatabase*/
        }
    }

}

/*
object DataComponentObject {

    lateinit var dataComponent: DataComponent

    fun initDataComponent() {
        dataComponent = DaggerDataComponent.builder()
            .dataDependencies(DataDependenciesProvider.dependencies)
            .build()
    }

}*/
