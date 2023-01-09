package com.example.myfitnesslogger2023.ui.sleepAndInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentSleepAndInfoBinding
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SleepAndInfoFragment : SendInfoBaseFragment() {

    private lateinit var sleepAndInfoViewModel: SleepAndInfoViewModel
    private var _binding: FragmentSleepAndInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sleepAndInfoViewModel = ViewModelProvider(this).get(SleepAndInfoViewModel::class.java)

        _binding = FragmentSleepAndInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        this.initialize(sleepAndInfoViewModel, binding.SendButton, binding.circularProgress)

        this.initializeNumberPickers()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeNumberPickers() {
        binding.sleepDurationHours.maxValue = 23
        binding.sleepDurationHours.minValue = 0
        binding.sleepDurationHours.value = 8

        binding.sleepDurationMinutes.maxValue = 59
        binding.sleepDurationMinutes.minValue = 0
        binding.sleepDurationMinutes.value = 0
    }

    override fun reInitializeLabels() {
        GlobalScope.launch {
            val todaysSleepDurationHours = sleepAndInfoViewModel.getTodaysSleepDurationHours(activity)
            val todaysSleepDurationMinutes = sleepAndInfoViewModel.getTodaysSleepDurationMinutes(activity)
            val information = sleepAndInfoViewModel.getInformation(activity)

            if (_binding != null) {
                activity?.runOnUiThread {
                    this@SleepAndInfoFragment.initializeALabel(
                        this@SleepAndInfoFragment.binding.sleepDurationLabel,
                        R.string.sleepDuration,
                        todaysSleepDurationHours + ":" + todaysSleepDurationMinutes
                    )
                    this@SleepAndInfoFragment.initializeALabel(
                        this@SleepAndInfoFragment.binding.infoLabel,
                        R.string.info,
                        information
                    )

                    this@SleepAndInfoFragment.binding.sleepDurationHours.value = todaysSleepDurationHours.toIntOrNull() ?: 8
                    this@SleepAndInfoFragment.binding.sleepDurationMinutes.value = todaysSleepDurationMinutes.toIntOrNull() ?: 0
                }
            }
        }
    }

    override fun sendPreAction() {
        binding.sleepDurationHours.clearFocus()
        binding.sleepDurationMinutes.clearFocus()
        binding.infoTextInput.clearFocus()
    }

    override fun sendAction() {
        sleepAndInfoViewModel.sendSleepDuration(binding.sleepDurationHours.value, binding.sleepDurationMinutes.value, activity)
        sleepAndInfoViewModel.sendInformation(binding.infoTextInput.text.toString(), activity)
    }

}