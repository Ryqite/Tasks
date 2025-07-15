package com.example.week7.UIcomponents

import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week7.Data.News
import com.example.week7.NewsViewModel

/**
 * Главный экран приложения, отображающий список всех новостей
 *
 * @param news список новостей для этображения
 * @param navigateToDetailScreen лямбда-функция для перехода в [DetailScreen]
 * с целью просмотра деталей конкретной новости,
 * id которой принимается в качестве параметра
 */
@Composable
fun MainScreen(
    news: List<News>,
    navigateToDetailScreen: (String) -> Unit
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(news) { item ->
                NewsItem(
                    news = item,
                    onNewsItemClick = { itemId ->
                        navigateToDetailScreen(itemId)
                    })
            }
        }
    }
}