package com.example.week12.Domain.UseCases

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.Models.BooksFromNetwork
import javax.inject.Inject

class InsertNewBookUseCase @Inject constructor(private val booksDatabaseRepository: BooksDatabaseRepository) {
    suspend operator fun invoke(book: BooksFromDatabase){
        booksDatabaseRepository.insertNewBook(book)
    }
}