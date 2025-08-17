package com.example.week12.SettingsDatabaseUseCasesTest

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.SettingsRepository
import com.example.week12.Domain.UseCases.ChangeDarkThemeUseCase
import com.example.week12.Domain.UseCases.GetAllUsersUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ChangeDarkThemeUseCaseTest {
    private val repository = mockk<SettingsRepository>(relaxed = true)
    private val changeDarkThemeUseCaseTest = ChangeDarkThemeUseCase(repository)
    @Test
    fun `changeDarkThemeUseCase should call changeDarkTheme from repository`() = runTest {
        changeDarkThemeUseCaseTest()
        coVerify(exactly = 1 ) { repository.changeDarkTheme() }
    }
}