package com.example.compose_todo.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert
    suspend fun insertNewNote(note: Notes)
    @Update
    suspend fun updateNoteById(note: Notes)
    @Delete
    suspend fun deleteNoteById(note: Notes)
    @Query("SELECT * FROM Notes")
    fun getAllNotes(): Flow<List<Notes>>
}