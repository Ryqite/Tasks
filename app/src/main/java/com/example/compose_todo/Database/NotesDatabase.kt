package com.example.compose_todo.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun getNotesDao(): NotesDao

    companion object {
        @Volatile
        private var instanceDB: NotesDatabase? = null
        fun getDatabase(context: Context): NotesDatabase {
            return instanceDB ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "NotesDatabase"
                ).build()
                instanceDB = newInstance
                newInstance
            }
        }
    }
}