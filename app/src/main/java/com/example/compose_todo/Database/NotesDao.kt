package com.example.compose_todo.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для работы с базой данных [NotesDatabase]
 *
 * фукнция [insertNewNote] принимает заметку в качестве параметра
 * и вставляет ее в базу данных
 * фукнция [updateNote] принимает заметку в качестве параметра
 *  * и обновляет ее в базе данных
 *  фукнция [deleteNote] принимает заметку в качестве параметра
 *  * и удаляет ее из базы данных
 *  фукнция [getAllNotes] получает все заметки из базы данных
 */
@Dao
interface NotesDao {
    @Insert
    suspend fun insertNewNote(note: Notes)
    @Update
    suspend fun updateNote(note: Notes)
    @Delete
    suspend fun deleteNote(note: Notes)
    @Query("SELECT * FROM Notes")
    fun getAllNotes(): Flow<List<Notes>>
}