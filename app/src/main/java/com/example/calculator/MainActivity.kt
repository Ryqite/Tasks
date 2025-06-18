package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.calculator.databinding.MainScreenBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: MainScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<CalculatorFragment>(R.id.container)
                setReorderingAllowed(true)
            }
        }
    }
}
