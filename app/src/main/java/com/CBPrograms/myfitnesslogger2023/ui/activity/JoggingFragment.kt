package com.CBPrograms.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentJoggingBinding
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions

class JoggingFragment : TabulatorChildFragment() {

    private lateinit var joggingViewModel: JoggingViewModel
    private var binding: FragmentJoggingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.jogging).toString()
    }

    override fun sendPreAction() {
        xbinding.distanceKm.clearFocus()
        xbinding.distancem.clearFocus()
        xbinding.durationHr.clearFocus()
        xbinding.durationMin.clearFocus()
        xbinding.caloriesNP.clearFocus()
    }

    override fun sendAction() {
        joggingViewModel.sendActivityDistance(
            mathFunctions.getDoubleValue(
                xbinding.distanceKm.value,
                xbinding.distancem.value
            ), activity
        )

        joggingViewModel.sendActivityDuration(
            mathFunctions.getMinutes(
                xbinding.durationHr.value,
                xbinding.durationMin.value
            ), activity
        )

        joggingViewModel.sendActivityCalories(
            xbinding.caloriesNP.value, activity
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        joggingViewModel =ViewModelProvider(this).get(JoggingViewModel::class.java)
        joggingViewModel.initialize(this.requireActivity());

        // Inflate the layout for this fragment
        binding = FragmentJoggingBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        initializeDistanceActivityControls(
            xbinding.distanceKm,
            xbinding.distancem,
            xbinding.durationHr,
            xbinding.durationMin,
            xbinding.caloriesNP,
            45,
            6,
            10,
            1
        )

        return root
    }

    override fun initializeUI() {
        // initialize fragment controls
        this.initialize(joggingViewModel, xbinding.SendButton, xbinding.circularProgress)

        initializeDistanceActivityControls(
            distanceKmNP = xbinding.distanceKm,
            distancemNP = xbinding.distancem,
            durationHrNP = xbinding.durationHr,
            durationmNP = xbinding.durationMin,
            caloriesNP = xbinding.caloriesNP,
            distancekmMax = 48,
            durationHrMax = 8,
            10,
            1
        )
    }

    override fun initializeFlows() {
        this.observeTodaysDurationFlowAndSetControls(
            joggingViewModel.getPastInformationFlow(
                informationType.activityTime,
                false,
                0,
                activity),
            R.string.duration,
            this@JoggingFragment.binding?.durationLabel,
            this@JoggingFragment.binding?.durationHr,
            this@JoggingFragment.binding?.durationMin
        )

        this.observeTodaysDoubleFlowsAndSetControls(
            joggingViewModel.getPastInformationFlow(
                informationType.activityDistanceJogging,
                false,
                0,
                activity),
            R.string.distance,
            this@JoggingFragment.binding?.distanceLabel,
            this@JoggingFragment.binding?.distanceKm,
            this@JoggingFragment.binding?.distancem)

        this.observeTodaysDoubleFlowsAndSetControls(
            joggingViewModel.getPastInformationFlow(
                informationType.activityCalories,
                false,
                0,
                activity),
            R.string.calories,
            this@JoggingFragment.binding?.caloriesLabel,
            this@JoggingFragment.binding?.caloriesNP,
            null)
    }

}