package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentSleepDurationBinding
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.ui.activity.TabulatorChildFragment

class SleepDurationFragment : TabulatorChildFragment() {

    private lateinit var sleepDurationViewModel: SleepDurationViewModel
    private var binding: FragmentSleepDurationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun GetTitle(): String {
        return contextRef?.resources?.getText(R.string.sleepDuration).toString()
    }

    override fun sendPreAction() {
        xbinding.sleepDurationHours.clearFocus()
        xbinding.sleepDurationMinutes.clearFocus()
    }

    override fun sendAction() {
        sleepDurationViewModel.sendSleepDuration(
            xbinding.sleepDurationHours.value,
            xbinding.sleepDurationMinutes.value,
            activity)
    }

    override fun initializeUI() {
        // initialize fragment controls
        this.initialize(sleepDurationViewModel, xbinding.SendButton, xbinding.circularProgress)

        initializeDurationNumberPickers(xbinding.sleepDurationHours, xbinding.sleepDurationMinutes)
    }

    override fun initializeFlows() {
        this.observeTodaysDurationFlowAndSetControls(
            sleepDurationViewModel.getPastInformationFlow(
                informationType.sleepduration,
                false,
                0,
                activity
            ),
            R.string.sleepDuration,
            this@SleepDurationFragment.binding?.sleepDurationLabel,
            this@SleepDurationFragment.binding?.sleepDurationHours,
            this@SleepDurationFragment.binding?.sleepDurationMinutes
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sleepDurationViewModel =
            ViewModelProvider(this).get(SleepDurationViewModel::class.java)
        sleepDurationViewModel.initialize(this.requireActivity());

        binding = FragmentSleepDurationBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        return root
    }
}