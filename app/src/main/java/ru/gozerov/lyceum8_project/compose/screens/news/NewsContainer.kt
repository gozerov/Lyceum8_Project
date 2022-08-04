package ru.gozerov.lyceum8_project.compose.screens.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import ru.gozerov.lyceum8_project.compose.navigation.NavigationRouter
import ru.gozerov.lyceum8_project.compose.navigation.Screens
import ru.gozerov.lyceum8_project.compose.screens.news.news_details.NewsDetailsScreen
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.NewsList
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.NewsScreen

@Composable
fun NewsContainer(
    title: MutableState<String>,
    parentNavController: NavController,
    args: NewsList
) {
    NavigationRouter(
        startDestination = Screens.NewsListScreen,
        parentController = parentNavController,
        screens = listOf(
            Screens.NewsListScreen to { currentNavController, _ ->
                NewsScreen(title, args, currentNavController)
            },
            Screens.NewsDetailsScreen to { currentNavController, _ ->
                NewsDetailsScreen(currentNavController)
            }
        )
    )

}