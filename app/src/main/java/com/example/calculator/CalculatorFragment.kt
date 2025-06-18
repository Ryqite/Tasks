package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.calculator.databinding.CalculatorBinding

class CalculatorFragment : Fragment() {
    lateinit var binding: CalculatorBinding
    val operationLogs = mutableListOf<LogData>()
    val calculation = Calculation()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        parentFragmentManager.commit {
            replace<LogPageFragment>(R.id.container)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    /**
     * Starts new Activity (UserProfile)
     */
    private fun userProfile() {
        parentFragmentManager.commit {
            replace<UserProfileFragment>(R.id.container)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}