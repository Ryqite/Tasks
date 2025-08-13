package com.example.week12.Data.Mappers

import com.example.week12.Data.DataSource.Local.BooksDatabase
import com.example.week12.Data.Entity.DBO.BooksDbModel
import com.example.week12.Domain.Models.BooksFromDatabase

fun BooksDbModel.toBooksFromDatabase():BooksFromDatabase = BooksFromDatabase(
    id = id,
    title = title,
    description = description,
    image = image,
    rating = rating,
    publishedAt = publishedAt,
    isSaved = isSaved
)