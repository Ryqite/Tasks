package com.example.week9.Data.DTO

/**
 *  Класс представляющий источник публикации
 *
 * @property id ID источника, откуда взята статья
 * @property name Название источника, откуда взята статья
 */
data class Source(
    val id: String,
    val name: String
)