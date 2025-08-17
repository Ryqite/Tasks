package com.example.week12.SettingsDatabaseUseCasesTest

import android.content.Context
import com.example.week12.Domain.SettingsRepository
import com.example.week12.Domain.UseCases.ChangeDarkThemeUseCase
import com.example.week12.Domain.UseCases.SaveLanguageUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SaveLanguageUseCaseTest {
    private val repository = mockk<SettingsRepository>(relaxed = true)
    private val saveLanguageUseCaseTest = SaveLanguageUseCase(repository)
    @Test
    fun `saveLanguageUseCase should call saveLanguage from repository`() {
        val testLanguage = "ru"
        saveLanguageUseCaseTest(testLanguage)
        verify(exactly = 1) { repository.saveLanguage(testLanguage) }
    }
}