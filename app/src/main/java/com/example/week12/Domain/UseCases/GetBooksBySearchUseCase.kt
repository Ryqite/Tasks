package com.example.week12.Domain.UseCases

import com.example.week12.Domain.BooksNetworkRepository
import com.example.week12.Domain.Models.BooksFromNetwork
import javax.inject.Inject

class GetBooksBySearchUseCase @Inject constructor( private val booksNetworkRepository: BooksNetworkRepository) {
    suspend operator fun invoke(keyword: String): List<BooksFromNetwork>{
        return booksNetworkRepository.getBooksBySearch(keyword)
    }
}