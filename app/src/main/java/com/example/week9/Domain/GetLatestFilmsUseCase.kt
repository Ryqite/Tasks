package com.example.week9.Domain

class GetLatestFilmsUseCase(private val repository: FilmsRepository) {
   suspend operator fun invoke(): List<Films>{
        return repository.getLatestFilms()
    }
}