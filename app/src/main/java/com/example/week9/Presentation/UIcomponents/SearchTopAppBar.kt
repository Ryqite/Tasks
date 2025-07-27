package com.example.week9.Presentation.UIcomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onCloseSearch: () -> Unit
) {
    TopAppBar(
        title = {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("Поиск фильмов...") },
                singleLine = true
            )
        },
        navigationIcon = {
            IconButton(onClick = onCloseSearch) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Закрыть поиск"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.LightGray
        )
    )
}