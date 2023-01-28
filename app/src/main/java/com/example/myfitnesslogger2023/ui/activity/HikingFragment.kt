package com.example.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentHikingBinding

class HikingFragment : TabulatorChildFragment() {

    private var binding: FragmentHikingBinding? = null

    private lateinit var hikingViewModel : HikingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.hiking).toString()
    }

    override fun sendPreAction() {
        xbinding.distanceKm.clearFocus()
        xbinding.distancem.clearFocus()
        xbinding.durationHr.clearFocus()
        xbinding.durationMin.clearFocus()
        xbinding.caloriesNP.clearFocus()
    }

    override fun sendAction() {
        hikingViewModel.sendActivity(
            xbinding.distanceKm.value,
            xbinding.distancem.value,
            xbinding.durationHr.value,
            xbinding.durationMin.value,
            xbinding.caloriesNP.value
        );
    }

    override fun reInitializeLabels() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHikingBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        initializeDistanceActivityControls(
            distanceKmNP = xbinding.distanceKm,
            distancemNP = xbinding.distancem,
            durationHrNP = xbinding.durationHr,
            durationmNP = xbinding.durationMin,
            caloriesNP = xbinding.caloriesNP,
            distancekmMax = 100,
            durationHrMax = 24,
            25,
            5
        )

        hikingViewModel = ViewModelProvider(this).get(HikingViewModel::class.java)

        initialize(hikingViewModel, binding?.SendButton, binding?.circularProgress)

        return root
    }

}
