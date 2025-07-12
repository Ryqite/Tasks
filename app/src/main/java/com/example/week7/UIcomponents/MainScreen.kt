package com.example.week7.UIcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.week7.Data.News
import com.example.week7.NewsViewModel

@Composable
fun MainScreen(
    news: List<News>,
    navigateToDetailScreen: (Int)->Unit
) {
    Scaffold(
//        bottomBar = {
//            BottomAppBar(
//                modifier = Modifier
//                    .background(Color.Black),
//                containerColor = Color.Black,
//                contentColor = Color.White
//            ) {
//                Row {
//                    IconButton(onClick = {}) {
//                        Icon(Icons.Default.Home, contentDescription = "Главная",
//                            tint = Color.White)
//                    }
//                    IconButton(onClick = {}) {
//                        Icon(Icons.Default.Favorite, contentDescription = "Закладки",
//                            tint = Color.White)
//                    }
//                }
//            }
//        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(news) { item ->
                NewsItem(
                    news = item,
                    navigateToDetailScreen = { navigateToDetailScreen(item.id) })
            }
        }
    }
}