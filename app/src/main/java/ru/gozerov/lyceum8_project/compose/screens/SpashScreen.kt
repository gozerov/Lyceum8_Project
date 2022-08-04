package ru.gozerov.lyceum8_project.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.gozerov.lyceum8_project.R
import ru.gozerov.lyceum8_project.compose.navigation.Screens.MainScreen
import ru.gozerov.lyceum8_project.compose.navigation.Screens.SplashScreen
import ru.gozerov.lyceum8_project.compose.navigation.navigate
import ru.gozerov.lyceum8_project.compose.screens.splash.state_machine.SplashIntent
import ru.gozerov.lyceum8_project.compose.screens.splash.state_machine.SplashViewState.*
import ru.gozerov.lyceum8_project.splash.SplashViewModel

@Composable
fun SplashScreen(navController: NavController) {
    val splashViewModel = hiltViewModel<SplashViewModel>()
    LaunchedEffect(key1 = Unit) {
        splashViewModel.handleIntent(SplashIntent.EnterScreen)
    }
    val state = splashViewModel.viewState.collectAsState(Loading)
    when(val value = state.value) {
        is Loading -> {
            SplashLoading()
        }
        is SuccessLoading -> {
            navController.navigate (
                route = MainScreen,
                args = bundleOf("KEY_NEWS" to value.newsList)
            ) {
                launchSingleTop = true
                popUpTo(SplashScreen) {
                    inclusive = true
                }
            }
        }
        is ErrorLoading -> {

        }
    }

}

@Composable
fun SplashLoading() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.white)
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

        }

    }
}