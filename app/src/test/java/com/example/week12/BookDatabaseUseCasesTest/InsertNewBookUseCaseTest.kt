package com.example.week12.BookDatabaseUseCasesTest

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.UseCases.InsertNewBookUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class InsertNewBookUseCaseTest {
    private val repository = mockk<BooksDatabaseRepository>(relaxed = true)
    private val insertNewBookUseCaseTest = InsertNewBookUseCase(repository)
    @Test
    fun `insertNewBookUseCase should call insertNewBook from repository`() = runTest{
        val testBook = BooksFromDatabase()
        insertNewBookUseCaseTest(testBook)
        coVerify(exactly = 1) { repository.insertNewBook(testBook) }
    }
}