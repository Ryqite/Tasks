package com.example.week12.Presentation.Screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.R

/**
 * Экран деталей приложения,отображающий данные выбранной новости
 *
 * @param certainfilms Экземпляр выбранной новости
 * @param navigateToMainScreen лямбда-функция для возврата на экран [MainScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSavedScreen(
    certainBook: BooksDatabaseItem?,
    navigateToMainScreen: () -> Unit,
    theme: AppTheme
) {
    var backButtonEnabled by remember { mutableStateOf(true) }
    val configuration = LocalConfiguration.current
    if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(R.string.DetailScreen)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (backButtonEnabled) {
                                    backButtonEnabled = false
                                    navigateToMainScreen()
                                }
                            },
                            enabled = backButtonEnabled
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "BackButton"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = when (theme) {
                            AppTheme.DARK -> Color.Black
                            AppTheme.LIGHT -> Color.LightGray
                        },
                        titleContentColor = when (theme) {
                            AppTheme.DARK -> Color.White
                            AppTheme.LIGHT -> Color.DarkGray
                        },
                        actionIconContentColor = when (theme) {
                            AppTheme.DARK -> Color.Black
                            AppTheme.LIGHT -> Color.DarkGray
                        }
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
                        model = certainBook?.image?.replace("http://", "https://"),
                        contentDescription = "PictureDetailScreen",
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("DetailAvatar")
                            .height(500.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .background(
                                when (theme) {
                                    AppTheme.DARK -> Color.DarkGray
                                    AppTheme.LIGHT -> Color.LightGray
                                }
                            )
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        certainBook?.title?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineMedium,
                                color = when (theme) {
                                    AppTheme.DARK -> Color.LightGray
                                    AppTheme.LIGHT -> Color.DarkGray
                                },
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .testTag("Title")
                            )
                        }
                        certainBook?.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                color = when (theme) {
                                    AppTheme.DARK -> Color.LightGray
                                    AppTheme.LIGHT -> Color.DarkGray
                                },
                                modifier = Modifier.testTag("Description")
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.Rating),
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = when (theme) {
                                    AppTheme.DARK -> Color.LightGray
                                    AppTheme.LIGHT -> Color.DarkGray
                                }
                            )
                            certainBook?.rating?.let {
                                Text(
                                    text = it.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = when (theme) {
                                        AppTheme.DARK -> Color.LightGray
                                        AppTheme.LIGHT -> Color.DarkGray
                                    },
                                    modifier = Modifier.testTag("Rating")
                                )
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.PublishedAt),
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = when (theme) {
                                    AppTheme.DARK -> Color.LightGray
                                    AppTheme.LIGHT -> Color.DarkGray
                                }
                            )
                            certainBook?.publishedAt?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    color = when (theme) {
                                        AppTheme.DARK -> Color.LightGray
                                        AppTheme.LIGHT -> Color.DarkGray
                                    },
                                    modifier = Modifier.testTag("PublishedAt")
                                )
                            }
                        }
                    }
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(R.string.DetailScreen)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (backButtonEnabled) {
                                    backButtonEnabled = false
                                    navigateToMainScreen()
                                }
                            },
                            enabled = backButtonEnabled
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "BackButton"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = when (theme) {
                            AppTheme.DARK -> Color.Black
                            AppTheme.LIGHT -> Color.LightGray
                        },
                        titleContentColor = when (theme) {
                            AppTheme.DARK -> Color.White
                            AppTheme.LIGHT -> Color.DarkGray
                        },
                        actionIconContentColor = when (theme) {
                            AppTheme.DARK -> Color.Black
                            AppTheme.LIGHT -> Color.DarkGray
                        }
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
                            model = certainBook?.image?.replace("http://", "https://"),
                            contentDescription = "PictureDetailScreen",
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .testTag("DetailAvatar")
                                .height(500.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .background(
                                    when (theme) {
                                        AppTheme.DARK -> Color.DarkGray
                                        AppTheme.LIGHT -> Color.LightGray
                                    }
                                )
                                .fillMaxWidth()
                        ) {
                            certainBook?.title?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = when (theme) {
                                        AppTheme.DARK -> Color.LightGray
                                        AppTheme.LIGHT -> Color.DarkGray
                                    },
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .testTag("Title")
                                )
                            }
                            certainBook?.description?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = when (theme) {
                                        AppTheme.DARK -> Color.LightGray
                                        AppTheme.LIGHT -> Color.DarkGray
                                    },
                                    modifier = Modifier.testTag("Description")
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(R.string.Rating),
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = when (theme) {
                                        AppTheme.DARK -> Color.LightGray
                                        AppTheme.LIGHT -> Color.DarkGray
                                    }
                                )
                                certainBook?.rating?.let {
                                    Text(
                                        text = it.toString(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = when (theme) {
                                            AppTheme.DARK -> Color.LightGray
                                            AppTheme.LIGHT -> Color.DarkGray
                                        },
                                        modifier = Modifier.testTag("Rating")
                                    )
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(R.string.PublishedAt),
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = when (theme) {
                                        AppTheme.DARK -> Color.LightGray
                                        AppTheme.LIGHT -> Color.DarkGray
                                    }
                                )
                                certainBook?.publishedAt?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                        color = when (theme) {
                                            AppTheme.DARK -> Color.LightGray
                                            AppTheme.LIGHT -> Color.DarkGray
                                        },
                                        modifier = Modifier.testTag("PublishedAt")
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}