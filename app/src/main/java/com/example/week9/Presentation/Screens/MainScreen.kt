package com.example.week9.Presentation.Screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.week9.Presentation.FilmsItem
import com.example.week9.Presentation.UIcomponents.DefaultTopAppBar
import com.example.week9.Presentation.UIcomponents.FilmsItemCard
import com.example.week9.Presentation.UIcomponents.SearchTopAppBar
import com.example.week9.R

/**
 * Главный экран приложения, отображающий список всех новостей
 *
 * @param films список новостей для этображения
 * @param navigateToDetailScreen лямбда-функция для перехода в [DetailScreen]
 * с целью просмотра деталей конкретной новости,
 * id которой принимается в качестве параметра
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    films: List<FilmsItem>,
    navigateToDetailScreen: (Int) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String
) {
    var showSearchField by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (showSearchField) {
                SearchTopAppBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = onSearchQueryChanged,
                    onCloseSearch = { showSearchField = false }
                )
            } else {
                DefaultTopAppBar(
                    onSearchClicked = { showSearchField = true }
                )
            }
        },
        content = { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(films) { item ->
                FilmsItemCard(
                    films = item,
                    onFilmsItemClick = { itemId ->
                        navigateToDetailScreen(itemId)
                    })
            }
        }
    })
}