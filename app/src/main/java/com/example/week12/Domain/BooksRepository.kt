package com.example.week12.Domain

import com.example.week12.Domain.Models.Books

interface BooksRepository {
   suspend fun getBooksBySearch(keyword:String):List<Books>
}