package ru.gozerov.lyceum8.screens.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.activity.MainActivity
import ru.gozerov.lyceum8.databinding.ActivitySplashBinding
import ru.gozerov.lyceum8.screens.splash.states.SplashViewState.*
import ru.gozerov.lyceum8.utils.appComponent
import ru.gozerov.lyceum8.utils.startRotateAnimation
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels { factory }

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var factory: SplashViewModel.SplashViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationContext.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.errorContainer.tryAgainButton.setOnClickListener {
            binding.errorContainer.container.visibility = View.GONE
            updateNews()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect { state ->
                when(state) {
                    is DefaultViewState -> {
                        updateNews()
                    }
                    is ErrorViewState -> {
                        binding.errorContainer.imgGear.startRotateAnimation()
                        binding.errorContainer.container.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
    private fun updateNews() {
        viewModel.updateNews {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
            finish()
        }
    }
}