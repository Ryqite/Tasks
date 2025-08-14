package com.example.week12.Data.DataSource.Local

import javax.inject.Inject

class SharedPreferencesDataSourceImpl @Inject constructor(private val appSharedPreferences: AppSharedPreferences): SharedPreferencesDataSource {
    override fun saveLanguage(language: String) {
        appSharedPreferences.saveLanguage(language)
    }

    override fun getLanguage(): String {
        return appSharedPreferences.getLanguage()
    }
}