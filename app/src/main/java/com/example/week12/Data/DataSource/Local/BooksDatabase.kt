package com.example.week12.Data.DataSource.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.week12.Data.Entity.DBO.BooksDbModel
import com.example.week12.Data.Entity.DBO.Profile

@Database(entities = [BooksDbModel::class, Profile::class], version = 2)
abstract class BooksDatabase: RoomDatabase() {
   abstract fun booksDao(): BooksDao
}