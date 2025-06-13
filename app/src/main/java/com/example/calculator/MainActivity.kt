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
        /**
         * this button call gets the input of editText field,
         * invoke the [count] function,
         * set textAnswer value to the [result] value
         * and storage expression information to [operationLogs]
         */
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
     * Starts new Activity (LogPage) and puts logs data in intent
     */
    private fun logPage() {
        val intent = Intent(this, LogPage::class.java)
            .putExtra("Operations", ArrayList(operationLogs))
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
        /**
         * [replace] in [expr] deletes spaces
         * [numbers] and [operators] are lists for storage numbers and operators of expression
         * [currentNum] keeps current number as string
         */
        if (input.isEmpty()) return 0.0
        val expr = input.replace(" ", "")
        val numbers = mutableListOf<Double>()
        val operators = mutableListOf<Char>()
        var currentNum = ""
        /**
         * this for cycle is used to fill lists. it sums chars until finds operator,
         * then adds both number and operator into lists
         */
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
        /**
         * this while cycle goes through operators list and finds operators '*' and '/',
         * after that it does mathematical method with nearest numbers of certain operator,
         * saves the result in first number and then deletes both operator and second number
         */
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
        /**
         * this while cycle works the same as last one except thing,
         * that it works with operators '+' and '-' and doesn't deletes
         * operators and number, but just steps to next ones
         */
        var result = numbers[0]
        i = 0
        while (i < operators.size) {
            when (operators[i]) {
                '+' -> result += numbers[i + 1]
                '-' -> result -= numbers[i + 1]
            }
            i++
        }
        return result
    }
}
