package com.example.week9.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.week9.Domain.GetLatestNewsUseCase

class NewsViewModelFactory(
    private val getLatestNewsUseCase: GetLatestNewsUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(
                getLatestNews = getLatestNewsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}