package com.CBPrograms.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentWorkoutBinding

class WorkoutFragment : TabulatorChildFragment() {

    private var binding: FragmentWorkoutBinding? = null

    private lateinit var workoutViewModel: WorkoutViewModel

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
        workoutViewModel.sendActivity(
            0,
            0,
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
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        initializeBsaeActivityControls(
            xbinding.durationHr,
            xbinding.durationMin,
            xbinding.caloriesNP,
            5,
            1
        )

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        initialize(workoutViewModel, binding?.SendButton, binding?.circularProgress)

        return root
    }

}
