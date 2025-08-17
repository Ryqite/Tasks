package com.example.week12.UseCaseTest.BookDatabaseUseCasesTest

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromNetwork
import com.example.week12.Domain.Models.ProfileParametersData
import com.example.week12.Domain.UseCases.GetAllBooksUseCase
import com.example.week12.Domain.UseCases.GetAllUsersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAllUsersUseCaseTest {
    private val repository = mockk<BooksDatabaseRepository>()
    private val getAllUsersUseCaseTest = GetAllUsersUseCase(repository)
    @Test
    fun `getAllUsersUseCase should return correct users from repository`() = runTest{
        val expectedUsers = listOf(
            ProfileParametersData(
                nickName = "Test nickname",
                img = "testLink",
                fullName = "FullName",
                password = "12345"
            )
        )
        coEvery { repository.getAllUsers() } returns expectedUsers
        val actualUsers = getAllUsersUseCaseTest()
        assertEquals(actualUsers,expectedUsers)
        coVerify(exactly = 1) { repository.getAllUsers() }
    }
}