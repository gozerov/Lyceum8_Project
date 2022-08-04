package ru.gozerov.lyceum8_project.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gozerov.domain.repository.NewsRepository
import ru.gozerov.lyceum8_project.data.cache.room.RoomNewsRepository
import ru.gozerov.lyceum8_project.data.cache.room.RoomNewsRepositoryImpl
import ru.gozerov.lyceum8_project.data.repository.NewsRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppBindModule {

    @Suppress("unused")
    @Singleton
    @Binds
    fun bindNewsRepImplToNewsRep(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Suppress("unused")
    @Binds
    fun bindRoomNewsRepImplToRoomNewsRep(roomNewsRepositoryImpl: RoomNewsRepositoryImpl): RoomNewsRepository

}