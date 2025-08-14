package com.example.week12.Domain

import android.content.Context
import com.example.week12.Domain.Models.AppTheme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val themeFlow: Flow<AppTheme>
    suspend fun changeDarkTheme()
    fun saveLanguage(language: String)
    fun getLanguage(): String
    fun setAppLocale(context: Context,language: String): Context
}