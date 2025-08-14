package com.example.week7.Domain

class GetLatestNewsUseCase(private val repository: NewsRepository) {
   suspend operator fun invoke(): List<News>{
        return repository.getLatestNews()
    }
}