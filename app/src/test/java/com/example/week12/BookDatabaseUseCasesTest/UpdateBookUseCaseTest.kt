package com.example.week12.BookDatabaseUseCasesTest

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.UseCases.InsertNewBookUseCase
import com.example.week12.Domain.UseCases.UpdateBookUseCase
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateBookUseCaseTest {
    private val repository = mockk<BooksDatabaseRepository>(relaxed = true)
    private val updateBookUseCaseTest = UpdateBookUseCase(repository)
    @Test
    fun `updateBookUseCase should call updateBook from repository`() = runTest{
        val testBook = BooksFromDatabase()
        updateBookUseCaseTest(testBook)
        coVerify(exactly = 1) { repository.updateBook(testBook) }
    }
}