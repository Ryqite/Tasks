package com.example.week5

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

 fun setAppLocale(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val resources = context.resources
    val config = Configuration(resources.configuration)

    config.setLocale(locale)
    config.setLocales(LocaleList(locale))
    context.createConfigurationContext(config)

    resources.updateConfiguration(config, resources.displayMetrics)
}