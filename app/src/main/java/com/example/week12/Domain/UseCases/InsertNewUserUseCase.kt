package com.example.week12.Domain.UseCases

import com.example.week12.Domain.BooksDatabaseRepository
import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Domain.Models.ProfileParametersData
import javax.inject.Inject

class InsertNewUserUseCase @Inject constructor(private val booksDatabaseRepository: BooksDatabaseRepository) {
    suspend operator fun invoke(user: ProfileParametersData){
        booksDatabaseRepository.insertNewUser(user)
    }
}