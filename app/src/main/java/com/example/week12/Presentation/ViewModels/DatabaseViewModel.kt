package com.example.week12.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week12.Domain.UseCases.DeleteBookUseCase
import com.example.week12.Domain.UseCases.GetAllBooksUseCase
import com.example.week12.Domain.UseCases.GetAllUsersUseCase
import com.example.week12.Domain.UseCases.InsertNewBookUseCase
import com.example.week12.Domain.UseCases.InsertNewUserUseCase
import com.example.week12.Domain.UseCases.UpdateBookUseCase
import com.example.week12.Presentation.Mappers.toBooksDatabaseItem
import com.example.week12.Presentation.Mappers.toBooksFromDatabase
import com.example.week12.Presentation.Mappers.toProfileData
import com.example.week12.Presentation.Mappers.toProfileParametersData
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.Presentation.Models.BooksNetworkItem
import com.example.week12.Presentation.Models.ProfileData
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
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val insertNewUserUseCase: InsertNewUserUseCase
) : ViewModel() {
    private val _booksFromDb = MutableStateFlow<List<BooksDatabaseItem>>(emptyList())
    val booksFromDb: StateFlow<List<BooksDatabaseItem>> = _booksFromDb
    private val _savedBooksIds = MutableStateFlow<Set<String>>(emptySet())
    val savedBooksIds: StateFlow<Set<String>> = _savedBooksIds

    private val _usersFromDb = MutableStateFlow<List<ProfileData>>(emptyList())
    val usersFromDb: StateFlow<List<ProfileData>> = _usersFromDb
    private val _currentUser = MutableStateFlow<ProfileData?>(null)
    val currentUser: StateFlow<ProfileData?> = _currentUser


    init {
        getAllBooksFromDb()
        getAllUsersFromDb()
    }

    fun insertNewBook(book: BooksDatabaseItem) {
        viewModelScope.launch {
            insertNewBookUseCase(book.toBooksFromDatabase())
            getAllBooksFromDb()
            _savedBooksIds.value += book.title
        }
    }
    fun insertNewUser(user: ProfileData) {
        viewModelScope.launch {
            insertNewUserUseCase(user.toProfileParametersData())
            getAllUsersFromDb()
            _currentUser.value = user
        }
    }
    fun loginUser(nickname: String, password: String): Boolean {
        val user = usersFromDb.value.find {
            it.nickName == nickname && it.password == password
        }
        return if (user != null) {
            _currentUser.value = user
            true
        } else {
            false
        }
    }
    fun logoutUser() {
        _currentUser.value = null
    }
    fun updateBook(book: BooksDatabaseItem) {
        viewModelScope.launch {
            updateBookUseCase(book.toBooksFromDatabase())
            getAllBooksFromDb()
        }
    }

    fun deleteBook(book: BooksDatabaseItem) {
        viewModelScope.launch {
            deleteBookUseCase(book.toBooksFromDatabase())
            getAllBooksFromDb()
            _savedBooksIds.value -= book.title
        }
    }

    private fun getAllBooksFromDb() {
        viewModelScope.launch {
            _booksFromDb.value = getAllBooksUseCase().map { it.toBooksDatabaseItem() }
            _savedBooksIds.value = _booksFromDb.value.map { it.title }.toSet()
        }
    }
    private fun getAllUsersFromDb() {
        viewModelScope.launch {
            _usersFromDb.value = getAllUsersUseCase().map { it.toProfileData() }
        }
    }
}