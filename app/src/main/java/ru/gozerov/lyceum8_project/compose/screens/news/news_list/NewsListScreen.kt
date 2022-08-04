package ru.gozerov.lyceum8_project.compose.screens.news.news_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ru.gozerov.domain.entity.news.News
import ru.gozerov.lyceum8_project.R
import ru.gozerov.lyceum8_project.compose.navigation.Screens
import ru.gozerov.lyceum8_project.compose.navigation.navigate
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine.NewsListIntent.EnterScreen
import ru.gozerov.lyceum8_project.compose.screens.news.news_list.state_machine.NewsListViewState.*
import ru.gozerov.lyceum8_project.compose.screens.reusable.Loading

@Composable
fun NewsScreen(
    appBarTitle: MutableState<String>,
    news: NewsList,
    navController: NavController
) {
    val viewModel = hiltViewModel<NewsListViewModel>()
    val viewState = viewModel.viewState.collectAsState()
    appBarTitle.value = stringResource(id = R.string.news_rus)

    LaunchedEffect(key1 = Unit) {
        viewModel.handleIntent(EnterScreen(news))
    }
    when (val value = viewState.value) {
        is Loading -> {
            Loading()
        }
        is ShowingData -> {
            NewsList(navController = navController, news = value.data)
        }
        is Error -> {

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsCard(
    news: News,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        onClick = onClick
    ) {
        Column {
            Text(
                text = news.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp)
            )
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(news.imageUrl)
                .size(Size.ORIGINAL)
                .build()
            AsyncImage(
                model = imageRequest,
                contentDescription = news.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                   .height(180.dp)
                   .padding(horizontal = 12.dp, vertical = 8.dp),
            )

        }

    }
}

@Composable
fun NewsList(navController: NavController, news: List<News>) {
    LazyColumn(
        content = {
            news.forEach { n ->
                item {
                    NewsCard(n) {
                        navController.navigate(
                            route = Screens.NewsDetailsScreen,
                            args = bundleOf("KEY_NEWS" to n)
                        )
                    }
                }
            }

        }
    )
}