package ru.gozerov.lyceum8.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.gozerov.lyceum8.data.cache.room.NewsDatabase

@Module(includes = [AppBindModule::class])
class AppModule {

    @Provides
    fun provideNewsDao(context: Context) = NewsDatabase.getInstance(context).getNewsDao()

    @Provides
    fun provideGson() = Gson()

}