package com.example.week12.Data.DataSource.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.week12.Data.Entity.DBO.BooksDbModel

@Database(entities = [BooksDbModel::class], version = 1)
abstract class BooksDatabase: RoomDatabase() {
   abstract fun booksDao(): BooksDao
}