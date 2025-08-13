package com.example.week12.Data.DataSource.Local

import com.example.week12.Data.Entity.DBO.BooksDbModel
import com.example.week12.Domain.Models.BooksFromNetwork
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertNewBook(book: BooksDbModel)

    suspend fun updateBook(book: BooksDbModel)

    suspend fun deleteBook(book: BooksDbModel)

    suspend fun getAllBooks(): List<BooksDbModel>

}