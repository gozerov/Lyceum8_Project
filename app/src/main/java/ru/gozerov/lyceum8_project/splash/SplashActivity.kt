package ru.gozerov.lyceum8_project.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ru.gozerov.lyceum8_project.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import ru.gozerov.lyceum8_project.activity.MainActivity
import javax.inject.Inject

/*
@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels { factory }

    @Inject
    lateinit var factory: SplashViewModel.SplashViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationContext.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launchWhenCreated {
            viewModel.event.collect { newsList ->
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.putExtra("newsList", NewsList(newsList))
                delay(1000)
                startActivity(intent)
                finish()
            }
        }
    }

}*/
