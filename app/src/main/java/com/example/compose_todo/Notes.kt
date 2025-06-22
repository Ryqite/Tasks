package com.example.compose_todo

data class Notes(
    val id: Int = 0,
    var content:String = "",
    var isDone:Boolean = false
)
