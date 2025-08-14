package com.example.week12.Data.Repository

import android.content.Context
import com.example.week12.Data.DataSource.Local.DataStoreSource
import com.example.week12.Data.DataSource.Local.SharedPreferencesDataSource
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Domain.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject
import android.content.res.Configuration
import android.os.LocaleList

class SettingsRepositoryImpl @Inject constructor(
    private val dataStoreSource: DataStoreSource,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) : SettingsRepository {
    override val themeFlow: Flow<AppTheme> =
        dataStoreSource.darkThemeFlow.map { isDark ->
            if (isDark) AppTheme.DARK else AppTheme.LIGHT
        }

    override suspend fun changeDarkTheme() {
        dataStoreSource.changeDarkTheme()
    }

    override fun saveLanguage(language: String) {
        sharedPreferencesDataSource.saveLanguage(language)
    }

    override fun getLanguage(): String {
        return sharedPreferencesDataSource.getLanguage()
    }

    override fun setAppLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        config.setLocales(LocaleList(locale))
        val newContext = context.createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
        return newContext
    }
}