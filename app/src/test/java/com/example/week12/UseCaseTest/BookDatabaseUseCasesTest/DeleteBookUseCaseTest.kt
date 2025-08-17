package com.example.week12.UseCaseTest.BookDatabaseUseCasesTest

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.UseCases.DeleteBookUseCase
import com.example.week12.Domain.UseCases.UpdateBookUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteBookUseCaseTest {
    private val repository = mockk<BooksDatabaseRepository>(relaxed = true)
    private val deleteBookUseCaseTest = DeleteBookUseCase(repository)
    @Test
    fun `deleteBookUseCase should call deleteBook from repository`() = runTest{
        val testBook = BooksFromDatabase()
        deleteBookUseCaseTest(testBook)
        coVerify(exactly = 1) { repository.deleteBook(testBook) }
    }
}