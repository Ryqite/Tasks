package com.example.week12.Domain

import com.example.week12.Domain.Models.BooksFromNetwork

interface BooksNetworkRepository {
   suspend fun getBooksBySearch(keyword:String):List<BooksFromNetwork>
}