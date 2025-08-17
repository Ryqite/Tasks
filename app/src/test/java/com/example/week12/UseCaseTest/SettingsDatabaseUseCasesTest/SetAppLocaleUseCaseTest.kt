package com.example.week12.UseCaseTest.SettingsDatabaseUseCasesTest

import android.content.Context
import com.example.week12.Domain.SettingsRepository
import com.example.week12.Domain.UseCases.ChangeDarkThemeUseCase
import com.example.week12.Domain.UseCases.SetAppLocaleUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SetAppLocaleUseCaseTest {
    private val repository = mockk<SettingsRepository>()
    private val setAppLocaleUseCaseTest = SetAppLocaleUseCase(repository)
    @Test
    fun `setAppLocaleUseCase should return new context from repository`() {
        val testContext = mockk<Context>(relaxed = true)
        val testLanguage = "ru"
        val expectedContext = mockk<Context>(relaxed = true)
        every { repository.setAppLocale(testContext,testLanguage) } returns expectedContext
        val actualContext = setAppLocaleUseCaseTest(testContext,testLanguage)
        assertEquals(expectedContext, actualContext)
        verify(exactly = 1) { repository.setAppLocale(testContext,testLanguage) }
    }
}