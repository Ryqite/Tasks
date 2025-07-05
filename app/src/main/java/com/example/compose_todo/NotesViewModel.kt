package com.example.compose_todo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_todo.Database.Notes
import com.example.compose_todo.Database.NotesDatabase
import kotlinx.coroutines.launch

/**
 *
 * ViewModel для управления операциями с заметками в приложении
 *
 * @param context Контекст приложения для инициализации базы данных
 * @constructor Создает экземпляр ViewModel с привязкой к контексту приложения
 *
 *  функция [insertNote] вызывает функцию [insertNewNote] в [NotesDao],
 *  передавая ей заметку в качестве параметра
 *  функция [updateNote] вызывает функцию [updateNote] в [NotesDao],
 *  передавая ей заметку в качестве параметра
 *  функция [deleteNote] вызывает функцию [deleteNote] в [NotesDao],
 *  передавая ей заметку в качестве параметра
 *  функция [getAllNotes] вызывает функцию [getAllNotes] в [NotesDao]
 */
class NotesViewModel(context: Context):ViewModel() {
    private val notesDao = NotesDatabase.getDatabase(context).getNotesDao()

    fun insertNote(note: Notes) = viewModelScope.launch {
        notesDao.insertNewNote(note)
    }
    fun updateNote(note: Notes) = viewModelScope.launch {
        notesDao.updateNote(note)
    }
    fun deleteNote(note: Notes) = viewModelScope.launch {
        notesDao.deleteNote(note)
    }
    fun getAllNotes() = notesDao.getAllNotes()
}