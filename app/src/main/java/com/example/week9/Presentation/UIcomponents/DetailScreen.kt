package com.example.week9.Presentation.UIcomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.week9.Presentation.FilmsItem
import com.example.week9.R

/**
 * Экран деталей приложения,отображающий данные выбранной новости
 *
 * @param certainfilms Экземпляр выбранной новости
 * @param navigateToMainScreen лямбда-функция для возврата на экран [MainScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    certainFilms: FilmsItem?,
    navigateToMainScreen:()->Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.DetailScreen))},
                navigationIcon = {
                    IconButton(onClick = navigateToMainScreen ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "BackButton")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.White
                )
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                AsyncImage(
                    model = certainFilms?.imageURL,
                    contentDescription = "PictureDetailScreen",
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("DetailAvatar")
                        .height(500.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    certainFilms?.title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                                .testTag("Title")
                        )
                    }
                    certainFilms?.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            modifier = Modifier.testTag("RatingKinopoisk")
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.Rating),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                        certainFilms?.rating?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black,
                                modifier = Modifier.testTag("RatingKinopoisk")
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.KinopoiskID),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                        certainFilms?.kinopoiskId?.let {
                            Text(
                                text = it.toString(),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color.Black,
                                modifier = Modifier.testTag("KinopoiskId")
                            )
                        }
                    }
                }
            }
        }
    }
}