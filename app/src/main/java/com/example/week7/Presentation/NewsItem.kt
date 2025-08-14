package com.example.week7.Presentation
/**
 * Класс, представляющий новостную статью для отображения в UI
 * (аналог [News] в Domain слое)
 *
 * Все поля имеют значения по умолчанию (пустые строки),
 * поэтому экземпляр может быть создан без параметров.
 *
 *  @property title Заголовок новости.
 *  @property imageURL Ссылка на изображение новости.
 *  @property source Источник новости (название издания/сайта).
 *  @property description Краткое описание новости.
 */
data class NewsItem(
    val title: String = "",
    val imageURL: String = "",
    val source: String = "",
    val description: String = ""
)
