package com.CBPrograms.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentWorkoutBinding
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions

class WorkoutFragment : TabulatorChildFragment() {

    private lateinit var workoutViewModel: WorkoutViewModel
    private var binding: FragmentWorkoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.workout).toString()
    }

    override fun sendPreAction() {
        xbinding.durationHr.clearFocus()
        xbinding.durationMin.clearFocus()
        xbinding.caloriesNP.clearFocus()
    }

    override fun sendAction() {
        workoutViewModel.sendActivityDuration(
            mathFunctions.getMinutes(
                xbinding.durationHr.value,
                xbinding.durationMin.value
            ), activity
        )

        workoutViewModel.sendActivityCalories(
            xbinding.caloriesNP.value, activity
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutViewModel =ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.initialize(this.requireActivity());

        // Inflate the layout for this fragment
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        initializeBsaeActivityControls(
            xbinding.durationHr,
            xbinding.durationMin,
            xbinding.caloriesNP,
            5,
            1
        )

        return root
    }

    override fun initializeUI() {
        // initialize fragment controls
        this.initialize(workoutViewModel, xbinding.SendButton, xbinding.circularProgress)

        initializeBsaeActivityControls(
            durationHrNP = xbinding.durationHr,
            durationmNP = xbinding.durationMin,
            caloriesNP = xbinding.caloriesNP,
            6,
            2)
    }

    override fun initializeFlows() {
        this.observeTodaysDurationFlowAndSetControls(
            workoutViewModel.getPastInformationFlow(
                informationType.activityTime,
                false,
                0,
                activity),
            R.string.duration,
            this@WorkoutFragment.binding?.durationLabel,
            this@WorkoutFragment.binding?.durationHr,
            this@WorkoutFragment.binding?.durationMin
        )

        this.observeTodaysDoubleFlowsAndSetControls(
            workoutViewModel.getPastInformationFlow(
                informationType.activityCalories,
                false,
                0,
                activity),
            R.string.calories,
            this@WorkoutFragment.binding?.caloriesLabel,
            this@WorkoutFragment.binding?.caloriesNP,
            null)
    }
}
