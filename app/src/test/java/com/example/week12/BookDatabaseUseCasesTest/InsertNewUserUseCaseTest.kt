package com.example.week12.BookDatabaseUseCasesTest

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.Models.ProfileParametersData
import com.example.week12.Domain.UseCases.InsertNewUserUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class InsertNewUserUseCaseTest {
    private val repository = mockk<BooksDatabaseRepository>(relaxed = true)
    private val insertNewUserUseCaseTest = InsertNewUserUseCase(repository)
    @Test
    fun `insertNewUserUseCase should call insertNewUser from repository`() = runTest{
        val testUser = ProfileParametersData()
        insertNewUserUseCaseTest(testUser)
        coVerify(exactly = 1) { repository.insertNewUser(testUser) }
    }
}