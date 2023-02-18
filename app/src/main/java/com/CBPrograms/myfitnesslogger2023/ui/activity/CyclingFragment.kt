package com.CBPrograms.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentCyclingBinding
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo.InfoViewModel
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CyclingFragment : TabulatorChildFragment() {

    private lateinit var cyclingViewModel: CyclingViewModel
    private var binding: FragmentCyclingBinding? = null

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
        cyclingViewModel.sendActivityDistance(
            mathFunctions.getDoubleValue(
                xbinding.distanceKm.value,
                xbinding.distancem.value
            ), activity
        )

        cyclingViewModel.sendActivityDuration(
            mathFunctions.getMinutes(
                xbinding.durationHr.value,
                xbinding.durationMin.value
            ), activity
        )

        cyclingViewModel.sendActivityCalories(
            xbinding.caloriesNP.value, activity
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cyclingViewModel =
            ViewModelProvider(this).get(CyclingViewModel::class.java)
        cyclingViewModel.initialize(this.requireActivity());

        // Inflate the layout for this fragment
        binding = FragmentCyclingBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        return root
    }

    override fun initializeUI() {
        // initialize fragment controls
        this.initialize(cyclingViewModel, xbinding.SendButton, xbinding.circularProgress)

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
    }

    override fun initializeFlows() {

        this.observeTodaysDurationFlowAndSetControls(
            cyclingViewModel.getPastInformationFlow(
                informationType.activityTime,
                false,
                0,
                activity),
            R.string.duration,
            this@CyclingFragment.binding?.durationLabel,
            this@CyclingFragment.binding?.durationHr,
            this@CyclingFragment.binding?.durationMin
        )

        this.observeTodaysDoubleFlowsAndSetControls(
            cyclingViewModel.getPastInformationFlow(
                    informationType.activityDistanceCycling,
                false,
                0,
                activity),
            R.string.distance,
            this@CyclingFragment.binding?.distanceLabel,
            this@CyclingFragment.binding?.distanceKm,
            this@CyclingFragment.binding?.distancem)

        this.observeTodaysDoubleFlowsAndSetControls(
            cyclingViewModel.getPastInformationFlow(
                informationType.activityCalories,
                false,
                0,
                activity),
            R.string.calories,
            this@CyclingFragment.binding?.caloriesLabel,
            this@CyclingFragment.binding?.caloriesNP,
            null)
    }
}
