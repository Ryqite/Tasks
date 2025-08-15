package com.example.week12.Presentation.UIComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.week12.Domain.Models.AppTheme

@Composable
fun AppBottomAppBar(
    theme: AppTheme,
    navigateToMainScreen: () -> Unit,
    navigateToSavedScreen: () -> Unit
) {
    BottomAppBar(modifier = Modifier.fillMaxHeight(0.06f),
        containerColor = when (theme) {
            AppTheme.DARK -> Color.Black
            AppTheme.LIGHT -> Color.LightGray
        },
        contentColor = when (theme) {
            AppTheme.DARK -> Color.White
            AppTheme.LIGHT -> Color.DarkGray
        },
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = navigateToMainScreen) {
                    Icon(Icons.Default.Home, contentDescription = "MainIcon")
                }
                IconButton(onClick = navigateToSavedScreen) {
                    Icon(Icons.Default.Favorite, contentDescription = "SavedIcon")
                }
            }
        }
    )
}