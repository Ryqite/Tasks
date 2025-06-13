package com.example.calculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.MainScreenBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: MainScreenBinding
    val operationLogs = mutableListOf<LogData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonEquals.setOnClickListener {
            val input = binding.editTextText2.text.toString()
            val result = count(input).toString()
            binding.textAnswer.text = result
            operationLogs.add(LogData(input, result))
        }
        binding.buttonLog.setOnClickListener {
            logPage()
        }
        binding.buttonProfile.setOnClickListener {
            userProfile()
        }
    }

    /**
     * Starts new Activity (LogPage)
     */
    private fun logPage() {
        val intent =
            Intent(this, LogPage::class.java).putExtra("Operations", ArrayList(operationLogs))
        startActivity(intent)
    }

    /**
     * Starts new Activity (UserProfile)
     */
    private fun userProfile() {
        val intent = Intent(this, UserProfile::class.java)
        startActivity(intent)
    }

    /**
     * @param input its expression from the EditText
     * @return the result of expression [input]
     */
    private fun count(input: String): Double {
        if (input.isEmpty()) return 0.0
        val expr = input.replace(" ", "")
        val numbers = mutableListOf<Double>()
        val operators = mutableListOf<Char>()
        // Разбиваем на числа и операторы
        var currentNum = ""
        for (char in expr) {
            if (char in "+-*/") {
                numbers.add(currentNum.toDouble())
                operators.add(char)
                currentNum = ""
            } else {
                currentNum += char
            }
        }
        numbers.add(currentNum.toDouble())
        // Сначала обрабатываем * и /
        var i = 0
        while (i < operators.size) {
            when (operators[i]) {
                '*' -> {
                    numbers[i] = numbers[i] * numbers[i + 1]
                    numbers.removeAt(i + 1)
                    operators.removeAt(i)
                }

                '/' -> {
                    numbers[i] = numbers[i] / numbers[i + 1]
                    numbers.removeAt(i + 1)
                    operators.removeAt(i)
                }

                else -> i++
            }
        }
        // Затем обрабатываем + и -
        var result = numbers[0]
        for (i in operators.indices) {
            when (operators[i]) {
                '+' -> result += numbers[i + 1]
                '-' -> result -= numbers[i + 1]
            }
        }
        return result
    }
}
