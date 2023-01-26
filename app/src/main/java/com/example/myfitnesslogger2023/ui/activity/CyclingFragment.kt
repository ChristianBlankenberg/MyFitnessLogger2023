package com.example.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentCyclingBinding

class CyclingFragment : TabulatorChildFragment() {

    private var _binding: FragmentCyclingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.cycling).toString()
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
        _binding = FragmentCyclingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializeDistanceActivityControls(
            distanceKmNP = binding.distanceKm,
            distancemNP = binding.distancem,
            durationHrNP = binding.durationHr,
            durationmNP = binding.durationMin,
            caloriesNP = binding.caloriesNP,
            distancekmMax = 300,
            durationHrMax = 18,
            30,
            1
        )


        return root
    }

}
