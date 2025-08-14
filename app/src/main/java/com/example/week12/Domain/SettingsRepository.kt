package com.example.week12.Domain

import com.example.week12.Domain.Models.AppTheme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val themeFlow: Flow<AppTheme>
    suspend fun changeDarkTheme()
}