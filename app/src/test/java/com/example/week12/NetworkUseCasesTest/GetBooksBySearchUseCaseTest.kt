package com.example.week12.NetworkUseCasesTest

import com.example.week12.Domain.BooksNetworkRepository
import com.example.week12.Domain.Models.BooksFromNetwork
import com.example.week12.Domain.UseCases.GetBooksBySearchUseCase
import io.mockk.coEvery
import org.junit.Assert.*
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetBooksBySearchUseCaseTest {
    private val repository = mockk<BooksNetworkRepository>()
    private val getBooksBySearchUseCaseTest = GetBooksBySearchUseCase(repository)
    @Test
    fun `getBooksBySearch should return correct data from repository`() = runTest{
        val expectedFilms = listOf(
            BooksFromNetwork(
                title = "Test title",
                image = "testLink",
                description = "Very good book",
                rating = 4.0,
                publishedAt = "2021"

            )
        )
        coEvery { repository.getBooksBySearch("Romantic") } returns expectedFilms
        val result = getBooksBySearchUseCaseTest("Romantic")
        assertEquals(result,expectedFilms)
        coVerify(exactly = 1) { repository.getBooksBySearch("Romantic") }
    }
}