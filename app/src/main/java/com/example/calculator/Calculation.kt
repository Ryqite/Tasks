package com.example.calculator

import java.util.*
import kotlin.math.pow

class Calculation {
    /**
     * Вычисляет результат выражения
     * @param input выражение из EditText (например, "2+3*4")
     * @return результат вычисления выражения
     * @throws IllegalArgumentException если выражение содержит недопустимые символы
     */
    fun count(input: String): Double {
        if (input.isEmpty()) return 0.0
        /**
         *  массив символов [tokens] выражения после удаления пробелов
         * очередь [outputQueue] для хранения чисел и операторов в постфиксной нотации
         * стек [operatorStack] для временного хранения операторов
         */
        val tokens = input.replace(" ", "").toCharArray()
        val outputQueue = LinkedList<String>()
        val operatorStack = Stack<Char>()

        /**
         * Цикл преобразует инфиксное выражение в постфиксное
         * используя алгоритм сортировочной станции
         */
        var i = 0
        while (i < tokens.size) {
            when {
                /**
                 * Собирает последовательность цифр и точки в число
                 * и добавляет в выходную очередь
                 */
                tokens[i].isDigit() || tokens[i] == '.' -> {
                    val numStr = StringBuilder()
                    while (i < tokens.size && (tokens[i].isDigit() || tokens[i] == '.')) {
                        numStr.append(tokens[i++])
                    }
                    outputQueue.add(numStr.toString())
                    continue
                }
                /**
                 * Переносит операторы из стека в выходную очередь
                 * пока приоритет текущего оператора не станет выше
                 * чем у оператора на вершине стека
                 */
                tokens[i] in "+-*/^" -> {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(' &&
                        getPrecedence(operatorStack.peek()) >= getPrecedence(tokens[i])
                    ) {
                        outputQueue.add(operatorStack.pop().toString())
                    }
                    operatorStack.push(tokens[i])
                }
                /**
                 * Помещает скобку в стек операторов
                 */
                tokens[i] == '(' -> {
                    operatorStack.push(tokens[i])
                }
                /**
                 * Переносит операторы из стека в выходную очередь
                 * до встречи соответствующей открывающей скобки
                 */
                tokens[i] == ')' -> {
                    while (operatorStack.peek() != '(') {
                        outputQueue.add(operatorStack.pop().toString())
                    }
                    operatorStack.pop()
                }
            }
            i++
        }
        /**
         * Перенос оставшихся операторов из стека в выходную очередь
         */
        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop().toString())
        }
        return evaluatePostfix(outputQueue)
    }

    /**
     * Определяет приоритет операций
     * @param op символ оператора
     */
    private fun getPrecedence(op: Char): Int {
        return when (op) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^' -> 3
            else -> 0
        }
    }

    /**
     * Вычисляет значение выражения в постфиксной нотации
     * @param queue очередь с выражением в постфиксной нотации
     */
    private fun evaluatePostfix(queue: LinkedList<String>): Double {
        val stack = Stack<Double>()
        /**
         * - Числа помещаются в стек
         * - Операторы извлекают два числа из стека
         *   применяют операцию и помещают результат обратно
         */
        for (token in queue) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                token in "+-*/^" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    try {
                        stack.push(
                            when (token) {
                                "+" -> a + b
                                "-" -> a - b
                                "*" -> a * b
                                "/" -> a / b
                                "^" -> a.pow(b)
                                else -> throw IllegalArgumentException("Некорректный символ")
                            }
                        )
                    } catch (error: IllegalArgumentException) {
                        println("Некорректный символ: ${error.message}")
                    }
                }
            }
        }
        return stack.pop()
    }
}