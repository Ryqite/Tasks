package com.example.week12.Domain.UseCases

import com.example.week12.Domain.SettingsRepository
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): String{
       return settingsRepository.getLanguage()
    }
}