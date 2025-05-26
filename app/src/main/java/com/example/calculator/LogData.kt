package com.example.calculator

import java.io.Serializable

data class LogData(
    val expression: String,
    val result: String
): Serializable