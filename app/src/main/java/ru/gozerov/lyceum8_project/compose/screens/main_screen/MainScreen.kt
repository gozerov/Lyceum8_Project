package ru.gozerov.lyceum8_project.compose.screens.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.gozerov.lyceum8_project.compose.navigation.getArguments
import ru.gozerov.lyceum8_project.R
import ru.gozerov.lyceum8_project.compose.navigation.Screens
import ru.gozerov.lyceum8_project.compose.screens.management_structure.ManagementStructureScreen
import ru.gozerov.lyceum8_project.compose.screens.news.NewsContainer
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.NewsList


@Composable
fun MainScreen(parentNavController: NavController) {
    val news = parentNavController.getArguments()?.getParcelable<NewsList>("KEY_NEWS")
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val title = remember { mutableStateOf("") }

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerItem(
                title = stringResource(id = R.string.news_rus),
                navDestination = Screens.NewsListScreen,
                navController = navController,
                coroutineScope = coroutineScope,
                drawerState = drawerState
            )
            DrawerItem(
                title = stringResource(id = R.string.library_rus),
                navDestination = Screens.LibraryScreen,
                navController = navController,
                coroutineScope = coroutineScope,
                drawerState = drawerState
            )
            DrawerItem(
                title = stringResource(id = R.string.profile_rus),
                navDestination = Screens.ProfileScreen,
                navController = navController,
                coroutineScope = coroutineScope,
                drawerState = drawerState
            )
            DrawerItem(
                title = stringResource(id = R.string.schools_structure_rus),
                navDestination = Screens.ManagementStructureScreen,
                navController = navController,
                coroutineScope = coroutineScope,
                drawerState = drawerState
            )
        },
        drawerContentColor = Color.Black,
        content = {
        Column {
            TransparentTopAppBar(title, coroutineScope, drawerState)
            NavHost(
                navController = navController,
                startDestination = Screens.NewsListScreen
            ) {
                composable(Screens.NewsListScreen) {
                    news?.let { newsList ->
                        NewsContainer(
                            title = title,
                            parentNavController = parentNavController,
                            args = newsList
                        )

                    }

                }
                composable(Screens.LibraryScreen) {
                    Text(text = "d")
                }
                composable(Screens.ProfileScreen) {
                    Text(text = "ds")
                }
                composable(Screens.ManagementStructureScreen) {
                    ManagementStructureScreen()
                }
            }
        }
    })
}

@Composable
fun TransparentTopAppBar(
    title: MutableState<String>,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Spacer(
            modifier = Modifier.width(12.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(36.dp)
                .clickable {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu Icon",
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentSize()

            )
        }

        Text(
            text = title.value,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}