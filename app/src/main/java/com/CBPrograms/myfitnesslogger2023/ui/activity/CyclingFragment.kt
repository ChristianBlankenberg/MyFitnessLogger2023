package com.CBPrograms.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentCyclingBinding

class CyclingFragment : TabulatorChildFragment() {

    private var binding: FragmentCyclingBinding? = null

    private lateinit var cyclingViewModel : CyclingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.cycling).toString()
    }

    override fun sendPreAction() {
        xbinding.distanceKm.clearFocus()
        xbinding.distancem.clearFocus()
        xbinding.durationHr.clearFocus()
        xbinding.durationMin.clearFocus()
        xbinding.caloriesNP.clearFocus()
    }

    override fun sendAction() {
        cyclingViewModel.sendActivity(
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
        binding = FragmentCyclingBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        initializeDistanceActivityControls(
            distanceKmNP = xbinding.distanceKm,
            distancemNP = xbinding.distancem,
            durationHrNP = xbinding.durationHr,
            durationmNP = xbinding.durationMin,
            caloriesNP = xbinding.caloriesNP,
            distancekmMax = 300,
            durationHrMax = 18,
            30,
            1
        )

        cyclingViewModel = ViewModelProvider(this).get(CyclingViewModel::class.java)

        initialize(cyclingViewModel, binding?.SendButton, binding?.circularProgress)

        return root
    }

}
