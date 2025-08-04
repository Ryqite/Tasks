package com.example.week9.Domain.UseCases

import com.example.week9.Domain.Data.Films
import com.example.week9.Domain.FilmsRepository
import javax.inject.Inject

class GetLatestFilmsUseCase @Inject constructor(private val repository: FilmsRepository) {
   suspend operator fun invoke(keyword: String): List<Films> {
        return repository.getLatestFilms(keyword)
    }
}