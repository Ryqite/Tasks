package com.example.calculator


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Test for [Calculation]
 * I used this article for information https://habr.com/ru/companies/sberbank/articles/825820/
 */
class CalculationTest {

    private val calculation = Calculation()

    @Test
    fun `5 plus 7 multiply 3 minus 9 divide by 6 equal 24,5`() {
        val expression = "5+7*3-9/6"
        val expected = 24.5
        val actual = calculation.count(expression)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `64 divide by 4 + 2 multiply 3 minus 5 equal 17`() {
        val expression = "64/4+2*3-5"
        val expected = 17.0
        val actual = calculation.count(expression)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `5 plus 7 multiply 2 minus 10 not equal 10`() {
        val expression = "5+7*2-10"
        val expected = 10.0
        val actual = calculation.count(expression)
        Assertions.assertNotEquals(expected, actual)
    }
}