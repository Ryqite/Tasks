package com.example.compose_todo.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "content")
    var content:String = "",
    @ColumnInfo(name = "isDone")
    var isDone:Boolean = false
)