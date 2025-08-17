package com.example.week12.BookDatabaseUseCasesTest

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.Models.ProfileParametersData
import com.example.week12.Domain.UseCases.GetAllBooksUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAllBooksUseCaseTest {
    private val repository = mockk<BooksDatabaseRepository>()
    private val getAllBooksUseCaseTest = GetAllBooksUseCase(repository)
    @Test
    fun `getAllBooksUseCase should return correct books from repository`() = runTest{
        val expectedBooks = listOf(
            BooksFromDatabase(
                id = 1,
                title = "test",
                description = "test description",
                image = "testLink",
                rating = 3.5,
                publishedAt = "2001",
                isSaved = false
            )
        )
        coEvery { repository.getAllBooks() } returns expectedBooks
        val actualUsers = getAllBooksUseCaseTest()
        assertEquals(actualUsers,expectedBooks)
        coVerify(exactly = 1) { repository.getAllBooks() }
    }
}