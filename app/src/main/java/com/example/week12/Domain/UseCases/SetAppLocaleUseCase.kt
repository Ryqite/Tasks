package com.example.week12.Domain.UseCases

import android.content.Context
import com.example.week12.Domain.SettingsRepository
import javax.inject.Inject

class SetAppLocaleUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke(context: Context, language: String): Context{
        return settingsRepository.setAppLocale(context,language)
    }
}