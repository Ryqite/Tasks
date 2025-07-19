package com.example.week7.Presentation.UIcomponents

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.week7.Domain.News

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