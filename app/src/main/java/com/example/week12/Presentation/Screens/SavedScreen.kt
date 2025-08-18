package com.example.week12.Presentation.Screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.Presentation.UIComponents.AppBottomAppBar
import com.example.week12.Presentation.UIComponents.SavedBookItemCard
import com.example.week12.Presentation.UIComponents.SavedScreenTopAppBar
import com.example.week12.Presentation.ViewModels.DatabaseViewModel


@Composable
fun SavedScreen(
    back: () -> Unit,
    deleteBook: (BooksDatabaseItem) -> Unit,
    savedBooks: List<BooksDatabaseItem>,
    navigateToDetailScreen: (String) -> Unit,
    navigateToMainScreen:()->Unit,
    navigateToSavedScreen:()->Unit,
    navigateToProfilePage: () -> Unit,
    changeTheme: () -> Unit,
    theme: AppTheme,
    changeLanguage: () -> Unit
) {
    Scaffold(
        topBar = {
            SavedScreenTopAppBar(
                back = back,
                navigateToProfilePage = navigateToProfilePage,
                changeTheme = changeTheme,
                theme = theme,
                changeLanguage = changeLanguage
            )
        },
        bottomBar = {
            AppBottomAppBar(
                theme = theme,
                navigateToMainScreen = navigateToMainScreen,
                navigateToSavedScreen = navigateToSavedScreen
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(savedBooks) { item ->
                    SavedBookItemCard(
                        book = item,
                        onFilmsItemClick = { itemId ->
                            navigateToDetailScreen(itemId)
                        },
                        theme = theme,
                        deleteBook = deleteBook
                    )
                }
            }
        })
}