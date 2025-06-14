package com.example.calculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.MainScreenBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: MainScreenBinding
    val operationLogs = mutableListOf<LogData>()
    val calculation = Calculation()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * this button call gets the input of editText field,
         * invoke the [count] function of [Calculation] class,
         * set textAnswer value to the [result] value
         * and storage expression information to [operationLogs]
         */
        binding.buttonEquals.setOnClickListener {
            val input = binding.editTextText2.text.toString()
            val result = calculation.count(input).toString()
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

}
