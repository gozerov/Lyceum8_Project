package ru.gozerov.lyceum8_project.compose.screens.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DrawerItem(
    title: String,
    navDestination: String,
    navController: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                navController.navigate(navDestination) {
                    launchSingleTop = true
                    restoreState = true
                }
                coroutineScope.launch {
                    drawerState.close()
                }
            }
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.CenterStart)
        )
    }
}