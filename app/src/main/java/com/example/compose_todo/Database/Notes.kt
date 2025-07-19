package com.example.compose_todo.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс данных, представляющий заметки в приложении
 *
 * @property id Уникальный идентификатор продукта
 * @property content Содержимое заметки
 * @property isDone Состояние чекбокса (включен/выключен)
 */
@Entity(tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "content")
    var content:String = "",
    @ColumnInfo(name = "isDone")
    var isDone:Boolean = false,
    @ColumnInfo(name = "temp")
    var temp:String = ""
)