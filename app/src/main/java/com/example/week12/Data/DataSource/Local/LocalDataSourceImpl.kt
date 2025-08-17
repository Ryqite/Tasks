package com.example.week12.Data.DataSource.Local

import com.example.week12.Data.Entity.DBO.BooksDbModel
import com.example.week12.Data.Entity.DBO.Profile
import com.example.week12.Domain.Models.BooksFromNetwork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val booksDao: BooksDao): LocalDataSource {
    override suspend fun insertNewBook(book: BooksDbModel) {
        booksDao.insertNewBook(book)
    }

    override suspend fun updateBook(book: BooksDbModel) {
        booksDao.updateBook(book)
    }

    override suspend fun deleteBook(book: BooksDbModel) {
        booksDao.deleteBook(book)
    }

    override suspend fun getAllBooks(): List<BooksDbModel> {
        return booksDao.getAllBooks()
    }

    override suspend fun insertNewUser(user: Profile) {
        booksDao.insertNewUser(user)
    }

    override suspend fun getAllUsers(): List<Profile> {
        return booksDao.getAllUsers()
    }
}