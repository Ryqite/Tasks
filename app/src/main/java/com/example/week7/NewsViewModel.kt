package com.example.week7

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7.Data.News
import com.example.week7.Data.NewsResponse
import com.example.week7.Mappers.toNews
import com.example.week7.Network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val _latestNews = MutableStateFlow<List<News>>(emptyList())
    val latestNews: StateFlow<List<News>> = _latestNews.asStateFlow()
    fun getLatestNews() = viewModelScope.launch {
        val result = RetrofitInstance.api.getLatestNews()
        _latestNews.value = result.results.map { it.toNews() }
    }

}