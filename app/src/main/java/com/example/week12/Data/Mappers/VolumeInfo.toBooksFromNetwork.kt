package com.example.week12.Data.Mappers

import com.example.week12.Data.Entity.DTO.VolumeInfo
import com.example.week12.Domain.Models.BooksFromNetwork

fun VolumeInfo.toBooksFromNetwork(): BooksFromNetwork = BooksFromNetwork(
    title = title?:"",
    description = description?:"",
    image = imageLinks?.thumbnail?:"",
    rating = ratingsCount?:0.0,
    publishedAt = publishedDate?:""
)