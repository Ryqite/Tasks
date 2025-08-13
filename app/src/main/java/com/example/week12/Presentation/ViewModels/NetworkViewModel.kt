package com.example.week12.Presentation.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week12.Domain.UseCases.GetBooksBySearchUseCase
import com.example.week12.Presentation.Mappers.toBooksItem
import com.example.week12.Presentation.Models.BooksItem
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
@OptIn(FlowPreview::class)
@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val getBooksBySearchUseCase: GetBooksBySearchUseCase
) : ViewModel() {
    private val _booksBySearch = MutableStateFlow<List<BooksItem>>(emptyList())
    val booksBySearch: StateFlow<List<BooksItem>> = _booksBySearch

    private val _searchQuery = MutableStateFlow("Romantic")
    val searchQuery: StateFlow<String> = _searchQuery

    private var searchJob: Job? = null

    init {
        startSearchCollector()
    }

    private fun startSearchCollector(){
        searchJob = viewModelScope.launch {
            _searchQuery
                .debounce(2000)
                .distinctUntilChanged()
                .collect{query->
                    if(query.isNotEmpty()) loadBooksBySearch(query)
                }
        }
    }
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
    fun cancelCollector(){
        searchJob?.cancel()
        _searchQuery.value = ""
        startSearchCollector()
    }
    private fun loadBooksBySearch(query: String) {
        try {
            viewModelScope.launch {
                _booksBySearch.value = getBooksBySearchUseCase(query).map { it.toBooksItem() }
            }
        } catch (e: Exception) {
            handleError(e)
        }
    }

    private fun handleError(e: Exception) {
        when (e) {
            is IOException -> Log.e("ErrorHandler", "Ошибка сети: ${e.message}")
            is HttpException -> when (e.code()) {
                401 -> Log.e("ErrorHandler", "Требуется авторизация")
                403 -> Log.e("ErrorHandler", "Доступ запрещен")
                404 -> Log.e("ErrorHandler", "Новости не найдены")
                in 500..599 -> Log.e("ErrorHandler", "Ошибка сервера")
                else -> Log.e("ErrorHandler", "HTTP ошибка: ${e.code()}")
            }

            else -> Log.e("ErrorHandler", "Неизвестная ошибка: ${e.message}")
        }
    }
}