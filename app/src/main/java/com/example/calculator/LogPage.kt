package com.example.calculator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.databinding.LogPageBinding


class LogPage : AppCompatActivity() {
    lateinit var binding: LogPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LogPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val operations=intent.getSerializableExtra("Operations") as List<LogData>
        binding.rvLog.layoutManager = LinearLayoutManager(this)
        binding.rvLog.adapter= LogAdapter(operations)
    }
}