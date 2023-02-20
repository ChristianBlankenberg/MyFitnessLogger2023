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
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions

class HikingFragment : TabulatorChildFragment() {

    private lateinit var hikingViewModel: HikingViewModel
    private var binding: FragmentHikingBinding? = null

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
        hikingViewModel.sendActivityDistance(
            mathFunctions.getDoubleValue(
                xbinding.distanceKm.value,
                xbinding.distancem.value
            ), activity
        )

        hikingViewModel.sendActivityDuration(
            mathFunctions.getMinutes(
                xbinding.durationHr.value,
                xbinding.durationMin.value
            ), activity
        )

        hikingViewModel.sendActivityCalories(
            xbinding.caloriesNP.value, activity
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hikingViewModel = ViewModelProvider(this).get(HikingViewModel::class.java)
        hikingViewModel.initialize(this.requireActivity());

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
            5)

        return root
    }

    override fun initializeUI() {
        // initialize fragment controls
        this.initialize(hikingViewModel, xbinding.SendButton, xbinding.circularProgress)

        initializeDistanceActivityControls(
            distanceKmNP = xbinding.distanceKm,
            distancemNP = xbinding.distancem,
            durationHrNP = xbinding.durationHr,
            durationmNP = xbinding.durationMin,
            caloriesNP = xbinding.caloriesNP,
            distancekmMax = 110,
            durationHrMax = 26,
            30,
            6
        )
    }

    override fun initializeFlows() {
        this.observeTodaysDurationFlowAndSetControls(
            hikingViewModel.getPastInformationFlow(
                informationType.activityTime,
                false,
                0,
                activity),
            R.string.duration,
            this@HikingFragment.binding?.durationLabel,
            this@HikingFragment.binding?.durationHr,
            this@HikingFragment.binding?.durationMin
        )

        this.observeTodaysDoubleFlowsAndSetControls(
            hikingViewModel.getPastInformationFlow(
                informationType.activityDistanceHiking,
                false,
                0,
                activity),
            R.string.distance,
            this@HikingFragment.binding?.distanceLabel,
            this@HikingFragment.binding?.distanceKm,
            this@HikingFragment.binding?.distancem)

        this.observeTodaysDoubleFlowsAndSetControls(
            hikingViewModel.getPastInformationFlow(
                informationType.activityCalories,
                false,
                0,
                activity),
            R.string.calories,
            this@HikingFragment.binding?.caloriesLabel,
            this@HikingFragment.binding?.caloriesNP,
            null)
    }

}
