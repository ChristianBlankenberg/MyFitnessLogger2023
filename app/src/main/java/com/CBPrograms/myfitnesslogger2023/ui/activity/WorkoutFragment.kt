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
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.DistanceActivityFragment

class WorkoutFragment : DistanceActivityFragment() {

    private var binding: FragmentWorkoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.workout).toString()
    }

    override fun getReinitializeLabels() : Triple<TextView?, TextView?, TextView?>
    {
        return Triple(null, xbinding.durationLabel, xbinding.caloriesLabel)
    }

    override fun getNumberPickerDataControls() : ArrayList<NumberPicker?>
    {
        return arrayListOf(
            null,
            null,
            xbinding.durationHr,
            xbinding.durationMin,
            xbinding.caloriesNP)
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

        sendActivityBaseViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        initialize(sendActivityBaseViewModel, binding?.SendButton, binding?.circularProgress)

        return root
    }

    override fun initializeUI() {
    }

    override fun initializeFlows() {
    }

}
