package ru.gozerov.lyceum8_project.compose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.lyceum8_project.compose.navigation.Screens
import ru.gozerov.lyceum8_project.compose.screens.SplashScreen
import ru.gozerov.lyceum8_project.compose.screens.main_screen.MainScreen
import ru.gozerov.lyceum8_project.ui.theme.Lyceum8Theme
import ru.gozerov.lyceum8_project.ui.theme.Teal700
import ru.gozerov.lyceum8_project.ui.theme.White

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lyceum8Theme {
                val navController = rememberNavController()
                val gradientColors = listOf(White, Teal700)
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = gradientColors,
                                start = Offset(0f, Float.POSITIVE_INFINITY),
                                end = Offset(Float.POSITIVE_INFINITY, 0f)
                            )
                        )
                    ) {
                    NavHost(navController = navController, startDestination = Screens.SplashScreen) {
                        composable(Screens.SplashScreen) { SplashScreen(navController) }
                        composable(Screens.MainScreen) { MainScreen(navController) }
                    }
                }
            }
        }
    }

}