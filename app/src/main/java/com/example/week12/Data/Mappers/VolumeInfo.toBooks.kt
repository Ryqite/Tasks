package com.example.week12.Data.Mappers

import com.example.week12.Data.DTO.VolumeInfo
import com.example.week12.Domain.Models.Books

fun VolumeInfo.toBooks(): Books = Books(
    title = title?:"",
    description = description?:"",
    image = imageLinks?.thumbnail?:"",
    rating = ratingsCount?:0,
    publishedAt = publishedDate?:""
)