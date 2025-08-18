package com.example.week12.Presentation.UIComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreenTopAppBar(
    back: () -> Unit,
    navigateToProfilePage: () -> Unit,
    changeTheme: () -> Unit,
    theme: AppTheme,
    changeLanguage: () -> Unit
) {
    var backButtonEnabled by remember { mutableStateOf(true) }
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.SavedScreen),
            modifier = Modifier.testTag("SavedBooks")) },
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
                AppTheme.DARK -> Color.White
                AppTheme.LIGHT -> Color.DarkGray
            }
        ),
        navigationIcon = {
            IconButton(onClick = {
                if (backButtonEnabled) {
                    backButtonEnabled = false
                    back()
                }
            },
                enabled = backButtonEnabled) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "BackIconSavedScreen")
            }
        },
        actions = {
            IconButton(onClick = navigateToProfilePage) {
                Icon(Icons.Default.AccountCircle, contentDescription = "ProfileIconSavedScreen")
            }
            TopAppBarMenu(changeTheme,changeLanguage)
        }
    )
}