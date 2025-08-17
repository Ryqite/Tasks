package com.example.week12.Data.DataSource.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.week12.Data.Entity.DBO.BooksDbModel
import com.example.week12.Data.Entity.DBO.Profile

@Dao
interface BooksDao {
    @Insert
    suspend fun insertNewBook(book: BooksDbModel)
    @Update
    suspend fun updateBook(book: BooksDbModel)
    @Delete
    suspend fun deleteBook(book: BooksDbModel)
    @Query("SELECT * FROM Books")
    suspend fun getAllBooks(): List<BooksDbModel>
    @Insert
    suspend fun insertNewUser(user: Profile)
    @Query("SELECT * FROM Users")
    suspend fun getAllUsers(): List<Profile>
}