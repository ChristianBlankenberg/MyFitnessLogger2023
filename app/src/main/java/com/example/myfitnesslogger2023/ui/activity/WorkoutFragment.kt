package com.example.myfitnesslogger2023.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentCyclingBinding
import com.example.myfitnesslogger2023.databinding.FragmentHikingBinding
import com.example.myfitnesslogger2023.databinding.FragmentJoggingBinding
import com.example.myfitnesslogger2023.databinding.FragmentWorkoutBinding

class WorkoutFragment(val parentContext : Context?) : TabulatorChildFragment(parentContext) {

    private var _binding: FragmentWorkoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun GetTitle(): String {
        return parentContext?.resources?.getText(R.string.workout).toString()
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

        return root
    }

}
