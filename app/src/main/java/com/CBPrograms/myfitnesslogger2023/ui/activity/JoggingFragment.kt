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
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.DistanceActivityFragment


class JoggingFragment : DistanceActivityFragment() {

    private var binding: FragmentJoggingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.jogging).toString()
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

        sendActivityBaseViewModel = ViewModelProvider(this).get(JoggingViewModel::class.java)

        initialize(sendActivityBaseViewModel, binding?.SendButton, binding?.circularProgress)

        return root
    }

    override fun initializeUI() {
    }

    override fun initializeFlows() {
    }

}