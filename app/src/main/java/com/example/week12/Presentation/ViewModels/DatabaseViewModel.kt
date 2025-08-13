package com.example.week12.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week12.Domain.UseCases.DeleteBookUseCase
import com.example.week12.Domain.UseCases.GetAllBooksUseCase
import com.example.week12.Domain.UseCases.InsertNewBookUseCase
import com.example.week12.Domain.UseCases.UpdateBookUseCase
import com.example.week12.Presentation.Mappers.toBooksDatabaseItem
import com.example.week12.Presentation.Mappers.toBooksFromDatabase
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.Presentation.Models.BooksNetworkItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val insertNewBookUseCase: InsertNewBookUseCase,
    private val updateBookUseCase: UpdateBookUseCase,
    private val deleteBookUseCase: DeleteBookUseCase,
    private val getAllBooksUseCase: GetAllBooksUseCase
) : ViewModel() {
    private val _booksFromDb = MutableStateFlow<List<BooksDatabaseItem>>(emptyList())
    val booksFromDb: StateFlow<List<BooksDatabaseItem>> = _booksFromDb

    init {
        getAllBooksFromDb()
    }

    fun insertNewBook(book: BooksDatabaseItem) {
        viewModelScope.launch {
            insertNewBookUseCase(book.toBooksFromDatabase())
        }
    }

    fun updateBook(book: BooksDatabaseItem) {
        viewModelScope.launch {
            updateBookUseCase(book.toBooksFromDatabase())
        }
    }

    fun deleteBook(book: BooksDatabaseItem) {
        viewModelScope.launch {
            deleteBookUseCase(book.toBooksFromDatabase())
        }
    }

    private fun getAllBooksFromDb() {
        viewModelScope.launch {
            _booksFromDb.value = getAllBooksUseCase().map { it.toBooksDatabaseItem() }
        }
    }
}