package com.example.calculator

import java.io.Serializable


data class LogData(
    /**
     * @property <expression> its mathematical example like 3+5
     * @property <result> its result of mathematical example like 8 (3+5=8)
     */
    val expression: String,
    val result: String
) : Serializable