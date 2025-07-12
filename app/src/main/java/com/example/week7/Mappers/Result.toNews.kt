package com.example.week7.Mappers

import com.example.week7.Data.Result
import com.example.week7.Data.News
fun Result.toNews(): News = News(
    title = title,
    source = source_url,
    description = description,
    imageURL = image_url
)