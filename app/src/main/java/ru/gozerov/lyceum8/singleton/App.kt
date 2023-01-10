package ru.gozerov.lyceum8.singleton

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.di.AppComponent
import ru.gozerov.lyceum8.di.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.e("TAG", it)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.update_app_topic))
    }
}