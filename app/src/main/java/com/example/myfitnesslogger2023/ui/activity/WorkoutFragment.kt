package com.example.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentWorkoutBinding
import com.example.myfitnesslogger2023.services.FirebaseCrashlyticsService

class WorkoutFragment : TabulatorChildFragment() {

    private var _binding: FragmentWorkoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.workout).toString()
    }

    override fun sendPreAction() {
    }

    override fun sendAction() {
    }

    override fun reInitializeLabels() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.SendButton.setOnClickListener {
            FirebaseCrashlyticsService.log("Test Crash 2")
            FirebaseCrashlyticsService.reportCrash(RuntimeException("Test Crash 2"))
        }

        return root
    }

}
