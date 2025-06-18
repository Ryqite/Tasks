package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.databinding.LogPageBinding


class LogPageFragment : Fragment() {
    lateinit var binding: LogPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = LogPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val operations = arguments?.getSerializable("Operations") as List<LogData>
        binding.rvLog.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLog.adapter = LogAdapter(operations)
        binding.buttonBackLogPage.setOnClickListener {
            backToMainScreen()
        }
    }

    fun backToMainScreen() {
        parentFragmentManager.popBackStack()
    }
}