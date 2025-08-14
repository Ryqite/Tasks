package com.example.week12.Data.DataSource.Local

import android.content.Context
import java.util.Locale

class AppSharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    fun saveLanguage(language: String){
        sharedPreferences.edit().putString("app_language",language).apply()
    }
    fun getLanguage():String{
        return sharedPreferences.getString("app_language", Locale.getDefault().language)?: "en"
    }
}
