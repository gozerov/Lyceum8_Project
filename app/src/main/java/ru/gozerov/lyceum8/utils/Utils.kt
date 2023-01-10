package ru.gozerov.lyceum8.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import ru.gozerov.lyceum8.di.AppComponent
import ru.gozerov.lyceum8.singleton.App

fun ImageView.startRotateAnimation() {
    val rotateAnimation = RotateAnimation(
        0f,
        360f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    rotateAnimation.duration = 2000L
    rotateAnimation.repeatCount = Animation.INFINITE
    this.startAnimation(rotateAnimation)
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

val Context?.sharedPrefs: SharedPreferences
    get() {
        return this?.applicationContext?.getSharedPreferences(Utils.SharedPreferences, Context.MODE_PRIVATE)
            ?: throw NullPointerException("CONTEXT IS NULL")
    }

object Utils {
    const val SharedPreferences = "SHARED_PREFERENCES8"
}