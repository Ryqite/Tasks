package com.example.week7.Presentation.UIcomponents

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.week7.Domain.News
import com.example.week7.Presentation.NewsItem

/**
 * Экран деталей приложения,отображающий данные выбранной новости
 *
 * @param certainNews Экземпляр выбранной новости
 * @param navigateToMainScreen лямбда-функция для возврата на экран [MainScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    certainNews: NewsItem?,
    navigateToMainScreen: () -> Unit
) {
    val configuration = LocalConfiguration.current
    if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = navigateToMainScreen) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "BackButton"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.LightGray,
                        titleContentColor = Color.White,
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
                        model = certainNews?.imageURL,
                        contentDescription = "PictureDetailScreen",
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("DetailAvatar")
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        certainNews?.title?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .testTag("Title")
                            )
                        }

                        certainNews?.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black,
                                modifier = Modifier.testTag("Description")
                            )
                        }
                        certainNews?.source?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color.Black,
                                modifier = Modifier.testTag("source")
                            )
                        }
                    }
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = navigateToMainScreen) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "BackButton"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.LightGray,
                        titleContentColor = Color.White,
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
                    Row {
                        AsyncImage(
                            model = certainNews?.imageURL,
                            contentDescription = "PictureDetailScreen",
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .testTag("DetailAvatar")
                                .height(250.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            certainNews?.title?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .testTag("Title")
                                )
                            }

                            certainNews?.description?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black,
                                    modifier = Modifier.testTag("Description")
                                )
                            }
                            certainNews?.source?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color.Black,
                                    modifier = Modifier.testTag("source")
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}