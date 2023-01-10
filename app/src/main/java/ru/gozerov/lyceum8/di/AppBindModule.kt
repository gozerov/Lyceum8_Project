package ru.gozerov.lyceum8.di

import dagger.Binds
import dagger.Module
import ru.gozerov.domain.repository.NewsRepository
import ru.gozerov.lyceum8.data.cache.room.RoomNewsRepository
import ru.gozerov.lyceum8.data.cache.room.RoomNewsRepositoryImpl
import ru.gozerov.lyceum8.data.repository.NewsRepositoryImpl
import ru.gozerov.lyceum8.data.rest.NewsSource
import ru.gozerov.lyceum8.data.rest.NewsSourceImpl
import javax.inject.Singleton

@Module
interface AppBindModule {

    @Suppress("unused")
    @Singleton
    @Binds
    fun bindNewsRepImplToNewsRep(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Singleton
    @Suppress("unused")
    @Binds
    fun bindRoomNewsRepImplToRoomNewsRep(roomNewsRepositoryImpl: RoomNewsRepositoryImpl): RoomNewsRepository

    @Suppress("unused")
    @Binds
    fun bindNewsSourceImplToNewsSource(newsSourceImpl: NewsSourceImpl): NewsSource

}