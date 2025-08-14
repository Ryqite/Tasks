package com.example.week12.Data.DataSource.Local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreSourceImpl @Inject constructor(private val appDataStore: AppDataStore): DataStoreSource {
    override val darkThemeFlow: Flow<Boolean> = appDataStore.darkThemeFlow

    override suspend fun changeDarkTheme() {
        appDataStore.changeDarkTheme()
    }
}