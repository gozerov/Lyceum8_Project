package ru.gozerov.lyceum8_project.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gozerov.lyceum8_project.screen.NewsRVFragment
import ru.gozerov.lyceum8_project.screen.SelectableNewsFragment
import ru.gozerov.lyceum8_project.singleton.App
import ru.gozerov.lyceum8_project.splash.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent /*: DataDependencies */ {

    fun inject(selectableNewsFragment: SelectableNewsFragment)
    fun inject(app: App)
    fun inject(splashActivity: SplashActivity)
    fun inject(newsRVFragment: NewsRVFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder

    }

}


