package com.example.week9.Domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestFilmsUseCase @Inject constructor(private val repository: FilmsRepository) {
   suspend operator fun invoke(keyword: String): List<Films> {
        return repository.getLatestFilms(keyword)
    }
}