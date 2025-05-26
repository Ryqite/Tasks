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
            var input = binding.editTextText2.text.toString()
            var result = count(input).toString()
            binding.textAnswer.text = result
            operationLogs.add(LogData(input,result))
        }
        binding.buttonLog.setOnClickListener {
            logPage()
        }
    }
    fun logPage(){
        val intent=Intent(this,LogPage::class.java)
            .putExtra("Operations", ArrayList(operationLogs))
        startActivity(intent)
    }
    fun count(input: String): Double{
        if (input.isEmpty()) return 0.0

        val operatorInd=input.indexOfFirst { it in "+-*/" }
        val num1 =input.trim().substring(0,operatorInd).toDouble()
        val num2 =input.trim().substring(operatorInd+1).toDouble()
        val operator=input[operatorInd]
        var result= when(operator){
            '+'->num1+num2
            '-'->num1-num2
            '*'->num1*num2
            '/'->num1/num2
            else -> 0
        }
        return result as Double
    }
}
