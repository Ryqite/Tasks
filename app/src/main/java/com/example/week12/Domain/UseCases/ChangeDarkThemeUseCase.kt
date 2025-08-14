package com.example.week12.Domain.UseCases

import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Domain.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeDarkThemeUseCase @Inject constructor (private val settingsRepository: SettingsRepository) {
    val themeState: Flow<AppTheme> = settingsRepository.themeFlow
    suspend operator fun invoke(){
        settingsRepository.changeDarkTheme()
    }
}