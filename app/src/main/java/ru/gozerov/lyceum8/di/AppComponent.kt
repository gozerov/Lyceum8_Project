package ru.gozerov.lyceum8.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gozerov.lyceum8.screens.news_list.NewsListFragment
import ru.gozerov.lyceum8.screens.selected_news.SelectedNewsFragment
import ru.gozerov.lyceum8.singleton.App
import ru.gozerov.lyceum8.screens.splash.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(selectedNewsFragment: SelectedNewsFragment)
    fun inject(splashActivity: SplashActivity)
    fun inject(newsListFragment: NewsListFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder

    }

}


