package com.example.week12.Data.DataSource.Local

import kotlinx.coroutines.flow.Flow

interface DataStoreSource {
    val darkThemeFlow: Flow<Boolean>
    suspend fun changeDarkTheme()
}