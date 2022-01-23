package ru.gozerov.lyceum8_project.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gozerov.lyceum8_project.data.cache.room.NewsDatabase

@Module(includes = [AppBindModule::class])
class AppModule {

    @Provides
    fun provideNewsDao(context: Context) = NewsDatabase.getInstance(context).getNewsDao()

}