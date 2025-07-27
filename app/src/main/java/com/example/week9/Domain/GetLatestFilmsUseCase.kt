package com.example.week9.Domain

import kotlinx.coroutines.flow.Flow

class GetLatestFilmsUseCase(private val repository: FilmsRepository) {
   suspend operator fun invoke(keyword: String): List<Films> {
        return repository.getLatestFilms(keyword)
    }
}