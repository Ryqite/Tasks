package com.example.week12.Domain.UseCases

import com.example.week12.Domain.BooksRepository
import com.example.week12.Domain.Models.Books
import javax.inject.Inject

class GetBooksBySearchUseCase @Inject constructor( private val booksRepository: BooksRepository) {
    suspend operator fun invoke(keyword: String): List<Books>{
        return booksRepository.getBooksBySearch(keyword)
    }
}