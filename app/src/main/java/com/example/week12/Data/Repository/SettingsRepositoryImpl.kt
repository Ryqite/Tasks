package com.example.week12.Data.Repository

import com.example.week12.Data.DataSource.Local.DataStoreSource
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Domain.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val dataStoreSource: DataStoreSource): SettingsRepository {
    override val themeFlow: Flow<AppTheme> =
        dataStoreSource.darkThemeFlow.map { isDark->
            if(isDark) AppTheme.DARK else AppTheme.LIGHT
        }

    override suspend fun changeDarkTheme() {
        dataStoreSource.changeDarkTheme()
    }
}