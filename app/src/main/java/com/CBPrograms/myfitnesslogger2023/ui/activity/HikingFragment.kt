package com.CBPrograms.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentHikingBinding
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.DistanceActivityFragment

class HikingFragment : DistanceActivityFragment() {

    private var binding: FragmentHikingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.hiking).toString()
    }

    override fun getReinitializeLabels() : Triple<TextView?, TextView?, TextView?>
    {
        return Triple(xbinding.distanceLabel, xbinding.durationLabel, xbinding.caloriesLabel)
    }

    override fun getNumberPickerDataControls() : ArrayList<NumberPicker?>
    {
        return arrayListOf(
            xbinding.distanceKm,
            xbinding.distancem,
            xbinding.durationHr,
            xbinding.durationMin,
            xbinding.caloriesNP)
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

        sendActivityBaseViewModel = ViewModelProvider(this).get(HikingViewModel::class.java)

        initialize(sendActivityBaseViewModel, binding?.SendButton, binding?.circularProgress)

        return root
    }

    override fun initializeUI() {
    }

    override fun initializeFlows() {
    }

}
