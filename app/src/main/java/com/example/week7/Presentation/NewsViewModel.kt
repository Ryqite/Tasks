package com.example.week7.Presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7.Domain.GetLatestNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getLatestNews: GetLatestNewsUseCase
) : ViewModel() {
    private val _latestNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val latestNews: StateFlow<List<NewsItem>> = _latestNews
    init {
        loadNewsItems()
    }
    private fun loadNewsItems(){
        viewModelScope.launch {
            _latestNews.value = getLatestNews().map { it.toNewsItem() }
        }
    }
}