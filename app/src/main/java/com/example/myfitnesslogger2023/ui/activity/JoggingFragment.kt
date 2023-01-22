package com.example.myfitnesslogger2023.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentJoggingBinding

class JoggingFragment(val parentContext : Context?) : TabulatorChildFragment(parentContext) {

    private var _binding: FragmentJoggingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun GetTitle(): String {
        return parentContext?.resources?.getText(R.string.jogging).toString()
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
        _binding = FragmentJoggingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializeDistanceActivityControls(
            binding.distanceKm,
            binding.distancem,
            binding.durationHr,
            binding.durationMin,
            45,
            6,
            10,
            1
        )

        return root
    }
}