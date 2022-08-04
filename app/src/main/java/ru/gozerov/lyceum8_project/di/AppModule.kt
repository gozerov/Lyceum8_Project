package ru.gozerov.lyceum8_project.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.gozerov.lyceum8_project.data.cache.room.NewsDatabase

@InstallIn(SingletonComponent::class)
@Module(includes = [AppBindModule::class])
class AppModule {

    @Provides
    fun provideNewsDao(@ApplicationContext context: Context) = NewsDatabase.getInstance(context).getNewsDao()

}