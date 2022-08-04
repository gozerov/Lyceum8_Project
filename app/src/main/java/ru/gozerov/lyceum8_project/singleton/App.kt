package ru.gozerov.lyceum8_project.singleton

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()
/*

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }*/
