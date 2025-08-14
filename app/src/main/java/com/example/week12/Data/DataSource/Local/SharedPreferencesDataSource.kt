package com.example.week12.Data.DataSource.Local

interface SharedPreferencesDataSource {
    fun saveLanguage(language: String)
    fun getLanguage(): String
}