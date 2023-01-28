package com.example.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentJoggingBinding

class JoggingFragment : TabulatorChildFragment() {

    private var binding: FragmentJoggingBinding? = null

    private lateinit var joggingViewModel : JoggingViewModel

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
        joggingViewModel.sendActivity(
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

        joggingViewModel = ViewModelProvider(this).get(JoggingViewModel::class.java)

        initialize(joggingViewModel, binding?.SendButton, binding?.circularProgress)

        return root
    }
}