package com.example.week12.SettingsDatabaseUseCasesTest

import android.content.Context
import com.example.week12.Domain.SettingsRepository
import com.example.week12.Domain.UseCases.ChangeDarkThemeUseCase
import com.example.week12.Domain.UseCases.GetLanguageUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetLanguageUseCaseTest {
    private val repository = mockk<SettingsRepository>()
    private val getLanguageUseCaseTest = GetLanguageUseCase(repository)
    @Test
    fun `getLanguageUseCase should return correct language from repository`() {
        val expectedLanguage = "ru"
        every { repository.getLanguage() } returns expectedLanguage
        val actualLanguage= getLanguageUseCaseTest()
        assertEquals(expectedLanguage, actualLanguage)
        verify(exactly = 1) {
            repository.getLanguage()
        }
    }
}