package com.example.week9.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.week9.Domain.GetLatestFilmsUseCase

class filmsViewModelFactory(
    private val getLatestFilmsUseCase: GetLatestFilmsUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(filmsViewModel::class.java)){
            return filmsViewModel(
                getLatestFilms = getLatestFilmsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}