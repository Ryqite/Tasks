package com.example.week12.Domain.UseCases

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.Models.BooksFromNetwork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor(private val booksDatabaseRepository: BooksDatabaseRepository) {
    suspend operator fun invoke(): List<BooksFromDatabase> {
      return booksDatabaseRepository.getAllBooks()
    }
}