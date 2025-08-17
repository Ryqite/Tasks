package com.example.week12.Domain

import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.Models.BooksFromNetwork
import com.example.week12.Domain.Models.ProfileParametersData
import kotlinx.coroutines.flow.Flow

interface BooksDatabaseRepository {
    suspend fun insertNewBook(book: BooksFromDatabase)
    suspend fun updateBook(book: BooksFromDatabase)
    suspend fun deleteBook(book: BooksFromDatabase)
    suspend fun getAllBooks(): List<BooksFromDatabase>
    suspend fun insertNewUser(user: ProfileParametersData)
    suspend fun getAllUsers(): List<ProfileParametersData>
}