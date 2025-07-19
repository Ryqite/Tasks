package com.example.week7.Domain
/**
 * Класс представляющий ответ из сетевого запроса
 * @property articles Список новостных статей [Article]
 * @property status Статус ответа API
 * @property totalResults Количество доступных результатов по запросу
 */
data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)