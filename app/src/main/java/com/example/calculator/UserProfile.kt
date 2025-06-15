package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.UserProfileBinding

class UserProfile : AppCompatActivity() {
    private lateinit var binding: UserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}