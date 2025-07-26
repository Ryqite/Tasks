package com.example.week9.Presentation
/**
 * Класс, представляющий новостную статью для отображения в UI
 * (аналог [films] в Domain слое)
 *
 * Все поля имеют значения по умолчанию (пустые строки),
 * поэтому экземпляр может быть создан без параметров.
 *
 *  @property title Заголовок новости.
 *  @property imageURL Ссылка на изображение новости.
 *  @property source Источник новости (название издания/сайта).
 *  @property description Краткое описание новости.
 */
data class FilmsItem(
    val title: String = "",
    val imageURL: String = "",
    val description: String = "",
    val kinopoiskId: Int = 0,
    val rating: String = ""
)
