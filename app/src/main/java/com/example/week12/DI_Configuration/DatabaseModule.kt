package com.example.week12.DI_Configuration

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.week12.Data.DataSource.Local.BooksDao
import com.example.week12.Data.DataSource.Local.BooksDatabase
import com.example.week12.Data.DataSource.Local.LocalDataSource
import com.example.week12.Data.DataSource.Local.LocalDataSourceImpl
import com.example.week12.Data.Repository.BooksDatabaseRepositoryImpl
import com.example.week12.Domain.BooksDatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSource{
    @Binds
    @Singleton
    abstract fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}
@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseRepository{
    @Binds
    @Singleton
    abstract fun bindDatabaseRepository(impl: BooksDatabaseRepositoryImpl): BooksDatabaseRepository
}
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS Users (
                image TEXT NOT NULL,
                nickname TEXT NOT NULL PRIMARY KEY,
                fullName TEXT NOT NULL,
                password TEXT NOT NULL
            )
        """)
        }
    }
    @Provides
    @Singleton
    fun provideBooksDatabase(@ApplicationContext context: Context): BooksDatabase{
        return Room.databaseBuilder(
                context,
                BooksDatabase::class.java,
                "BooksDatabase"
            )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
    @Provides
    @Singleton
    fun provideBooksDao(booksDatabase: BooksDatabase): BooksDao{
        return booksDatabase.booksDao()
    }
}