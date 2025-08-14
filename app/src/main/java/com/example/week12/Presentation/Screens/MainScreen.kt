package com.example.week12.Presentation.Screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Presentation.Models.BooksNetworkItem
import com.example.week12.Presentation.UIComponents.DefaultTopAppBar
import com.example.week12.Presentation.UIComponents.BookItemCard
import com.example.week12.Presentation.UIComponents.SearchTopAppBar


@Composable
fun MainScreen(
    books: List<BooksNetworkItem>,
    navigateToDetailScreen: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String,
    onCancelNewSearchBooks: () -> Unit,
    navigateToProfilePage: ()-> Unit,
    changeTheme: () -> Unit,
    theme: AppTheme
) {
    var showSearchField by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (showSearchField) {
                SearchTopAppBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = onSearchQueryChanged,
                    onCloseSearch = { showSearchField = false },
                    onCancelNewSearchFilms = onCancelNewSearchBooks,
                    theme = theme
                )
            } else {
                DefaultTopAppBar(
                    onSearchClicked = { showSearchField = true },
                    navigateToProfilePage = navigateToProfilePage,
                    changeTheme = changeTheme,
                    theme = theme
                )
            }
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(books) { item ->
                    BookItemCard(
                        book = item,
                        onFilmsItemClick = { itemId ->
                            navigateToDetailScreen(itemId)
                        },
                        theme = theme)
                }
            }
        })
}