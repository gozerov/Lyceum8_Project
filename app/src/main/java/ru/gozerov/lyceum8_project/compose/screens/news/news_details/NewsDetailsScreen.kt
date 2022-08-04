package ru.gozerov.lyceum8_project.compose.screens.news.news_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import ru.gozerov.domain.entity.news.News
import ru.gozerov.lyceum8_project.compose.navigation.getArguments

@Composable
fun NewsDetailsScreen(navController: NavController) {
    val news = navController.getArguments()?.getSerializable("KEY_NEWS") as? News
    val scrollState = rememberScrollState()
    news?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .verticalScroll(scrollState)


        ) {

            Text(
                text = news.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
            Text(
                text = news.description,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
            )

            Image(
                painter = rememberAsyncImagePainter(
                    model = news.imageUrl,
                    contentScale = ContentScale.Inside
                ),
                contentDescription = news.title,
                modifier = Modifier
                    .height(400.dp)
                    .padding(vertical = 8.dp)
            )



        }
    }

}