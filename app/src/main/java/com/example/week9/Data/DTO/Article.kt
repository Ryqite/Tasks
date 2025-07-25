package com.example.week9.Data.DTO

/**
 * Класс, представляющий новостную статью.
 *
 * @property author Автор статьи.
 * @property content Основное содержание статьи
 * @property description Краткое описание статьи.
 * @property publishedAt Дата и время публикации
 * @property source Источник публикации. Содержит [Source.id] и [Source.name].
 * @property title Заголовок статьи.
 * @property url Прямая ссылка на полную статью на сайте издателя.
 * @property urlToImage Ссылка на главное изображение статьи.
 *
 * @see Source Ссылка на класс источника публикации
 */
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)