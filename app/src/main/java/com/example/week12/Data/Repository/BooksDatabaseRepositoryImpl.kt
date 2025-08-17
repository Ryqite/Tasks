package com.example.week12.Data.Repository

import com.example.week12.Data.DataSource.Local.LocalDataSource
import com.example.week12.Data.Entity.DBO.Profile
import com.example.week12.Data.Mappers.toBooksDbModel
import com.example.week12.Data.Mappers.toBooksFromDatabase
import com.example.week12.Data.Mappers.toProfile
import com.example.week12.Data.Mappers.toProfileParametersData
import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.Models.ProfileParametersData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksDatabaseRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource):BooksDatabaseRepository {
    override suspend fun insertNewBook(book: BooksFromDatabase) {
        localDataSource.insertNewBook(book.toBooksDbModel())
    }

    override suspend fun updateBook(book: BooksFromDatabase) {
        localDataSource.updateBook(book.toBooksDbModel())
    }

    override suspend fun deleteBook(book: BooksFromDatabase) {
        localDataSource.deleteBook(book.toBooksDbModel())
    }

    override suspend fun getAllBooks(): List<BooksFromDatabase> {
        val response = localDataSource.getAllBooks()
        return response.map { it.toBooksFromDatabase() }
    }
    override suspend fun insertNewUser(user: ProfileParametersData) {
        localDataSource.insertNewUser(user.toProfile())
    }

    override suspend fun getAllUsers(): List<ProfileParametersData> {
        val response = localDataSource.getAllUsers()
        return response.map { it.toProfileParametersData() }
    }
}