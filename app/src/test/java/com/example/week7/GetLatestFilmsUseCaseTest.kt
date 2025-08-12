package com.example.week7

import com.example.week9.Domain.Data.Films
import com.example.week9.Domain.FilmsRepository
import com.example.week9.Domain.UseCases.GetLatestFilmsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.verify

class GetLatestFilmsUseCaseTest {
    private val repository = mockk<FilmsRepository>()
    private val getLatestFilmsUseCaseTest = GetLatestFilmsUseCase(repository)

    @Test
    fun `getLatestFilms should return correct data from repository`() = runTest{
        val expectedFilms = listOf(
            Films(
                title = "Test title",
                imageURL = "testLink",
                description = "Very good film",
                kinopoiskId = 333,
                rating = "4.9"
            )
        )
        coEvery { repository.getLatestFilms("f") } returns expectedFilms
        val result = getLatestFilmsUseCaseTest("f")
        assertEquals(result,expectedFilms)
        coVerify(exactly = 1) { repository.getLatestFilms("f") }
    }
}