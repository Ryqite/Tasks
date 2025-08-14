package com.example.week5

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

/**
 * Функция для смены языка, станавливает локаль приложения и возвращает новый контекст
 *
 * @param context контекст приложения или активности
 * @param language код языка (например "ru")
 * @return новый контекст с примененной локалью
 */
fun setAppLocale(context: Context, language: String): Context {
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