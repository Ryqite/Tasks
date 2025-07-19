package com.example.compose_todo.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Room база данных для заметок
 *
 * функция [getNotesDao] возвращает сущность [NotesDao] для работы с базой данных
 * функция [getDatabase] возвращает сущность базы данных типа [NotesDatabase]
 */
@Database(entities = [Notes::class], version = 2)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun getNotesDao(): NotesDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Notes ADD COLUMN temp TEXT NOT NULL DEFAULT ''")
            }
        }
        @Volatile
        private var instanceDB: NotesDatabase? = null
        fun getDatabase(context: Context): NotesDatabase {
            return instanceDB ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "NotesDatabase"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                instanceDB = newInstance
                newInstance
            }
        }
    }
}