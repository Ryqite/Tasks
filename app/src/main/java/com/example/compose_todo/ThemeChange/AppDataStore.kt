package com.example.compose_todo.ThemeChange
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class AppDataStore(private val dataStore: DataStore<Preferences>) {
    private val DARK_THEME = booleanPreferencesKey("dark_theme")

    val darkThemeFlow = dataStore.data
        .map { preferences -> preferences[DARK_THEME] ?: true }

    suspend fun changeDarkTheme() {
        dataStore.edit { settings ->
            val currentState = settings[DARK_THEME] ?: true
            settings[DARK_THEME] = !currentState
        }
    }
}