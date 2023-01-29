package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentWeightKfaSleepdurationInfoBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Math.abs

class WeightKFAFragment : SendInfoBaseFragment() {

    private lateinit var weightAndKFAViewModel: WeightKFASleepInfoViewModel
    private var binding: FragmentWeightKfaSleepdurationInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val xbinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weightAndKFAViewModel =
            ViewModelProvider(this).get(WeightKFASleepInfoViewModel::class.java)
        weightAndKFAViewModel.initialize(this.requireActivity());

        binding = FragmentWeightKfaSleepdurationInfoBinding.inflate(inflater, container, false)
        val root: View = xbinding.root

        this.initialize(weightAndKFAViewModel, xbinding.SendButton, xbinding.circularProgress)

        // initialize fragment controls
        initializeNumberPickers()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getYesterdaysTodaysWeightInformation(todaysWeight: Double?): String {
        val yesterdaysWeight = this.weightAndKFAViewModel.getPastWeight(-1, activity)

        return getYesterdaysTodaysValueInformation(todaysWeight, yesterdaysWeight)
    }

    private fun getYesterdaysTodaysKFAInformation(todaysKFA: Double?): String {
        val yesterdaysKFA = this.weightAndKFAViewModel.getPastKFA(-1, activity)

        return getYesterdaysTodaysValueInformation(todaysKFA, yesterdaysKFA)
    }

    override fun reInitializeLabels() {
        GlobalScope.launch {
            val todaysWeight = weightAndKFAViewModel.getPastWeight(0, activity)
            val todaysKFA = weightAndKFAViewModel.getPastKFA(0, activity)

            val yesterdaysTodaysWeightInformation =
                this@WeightKFAFragment.getYesterdaysTodaysWeightInformation(todaysWeight)
            val yesterdaysTodaysKFAInformation =
                this@WeightKFAFragment.getYesterdaysTodaysKFAInformation(todaysKFA)

            val sleepdurationMinutes = weightAndKFAViewModel.getTodaysSleepDurationMinutes(activity).toIntOrNull() ?: 0

            val sleepdurationHoursAndMinutes = mathFunctions.getHoursAndMinutes(sleepdurationMinutes)

            val information = weightAndKFAViewModel.getInformation(activity)

            activity?.runOnUiThread {
                this@WeightKFAFragment.initializeALabel(
                    this@WeightKFAFragment.binding?.weightLabel,
                    R.string.weight,
                    yesterdaysTodaysWeightInformation
                )
                this@WeightKFAFragment.initializeALabel(
                    this@WeightKFAFragment.binding?.kfaLabel,
                    R.string.kfa,
                    yesterdaysTodaysKFAInformation
                )

                this@WeightKFAFragment.initializeALabel(
                    this@WeightKFAFragment.binding?.sleepDurationLabel,
                    R.string.sleepDuration,
                    sleepdurationHoursAndMinutes.first + ":" + sleepdurationHoursAndMinutes.second
                )
                this@WeightKFAFragment.initializeALabel(
                    this@WeightKFAFragment.binding?.infoLabel,
                    R.string.info,
                    information
                )

                val todaysWeightPreAndPastComma =
                    mathFunctions.getPreAndPastCommaValue(todaysWeight ?: 0.0)
                val todaysKFAComma = mathFunctions.getPreAndPastCommaValue(todaysKFA ?: 0.0)

                this@WeightKFAFragment.binding?.weightGreat?.value =
                    todaysWeightPreAndPastComma.first
                this@WeightKFAFragment.binding?.weightSmall?.value =
                    todaysWeightPreAndPastComma.second

                this@WeightKFAFragment.binding?.KFAGreat?.value = todaysKFAComma.first
                this@WeightKFAFragment.binding?.KFASmall?.value = todaysKFAComma.second

                this@WeightKFAFragment.binding?.sleepDurationHours?.value =
                    sleepdurationHoursAndMinutes.first.toIntOrNull() ?: 8
                this@WeightKFAFragment.binding?.sleepDurationMinutes?.value =
                    sleepdurationHoursAndMinutes.second.toIntOrNull() ?: 0
            }
        }
    }

    override fun sendPreAction() {
        xbinding.weightGreat.clearFocus()
        xbinding.weightSmall.clearFocus()
        xbinding.KFAGreat.clearFocus()
        xbinding.KFASmall.clearFocus()
        xbinding.sleepDurationHours.clearFocus()
        xbinding.sleepDurationMinutes.clearFocus()
        xbinding.infoTextInput.clearFocus()
    }

    override fun sendAction() {
        weightAndKFAViewModel.sendData(
            xbinding.weightGreat.value,
            xbinding.weightSmall.value,
            xbinding.KFAGreat.value,
            xbinding.KFASmall.value,
            xbinding.KFASmall.isVisible,
            activity
        )

        weightAndKFAViewModel.sendSleepDuration(
            xbinding.sleepDurationHours.value,
            xbinding.sleepDurationMinutes.value,
            activity
        )
        weightAndKFAViewModel.sendInformation(xbinding.infoTextInput.text.toString(), activity)

    }

    private fun initializeNumberPickers() {
        xbinding.weightGreat.minValue = 72
        xbinding.weightGreat.maxValue = 85
        xbinding.weightGreat.value = 78

        xbinding.weightSmall.minValue = 0
        xbinding.weightSmall.maxValue = 9
        xbinding.weightSmall.value = 5

        xbinding.KFAGreat.minValue = 17
        xbinding.KFAGreat.maxValue = 25
        xbinding.KFAGreat.value = 21

        /*
        xbinding.KFAGreat.setFormatter {
            when (it) {
                17 -> {
                    xbinding.KFASmall.isVisible = false
                    xbinding.kfaComma.isVisible = false
                    "-"
                }
                else -> {
                    xbinding.KFASmall.isVisible = true
                    xbinding.kfaComma.isVisible = true
                    it.toString()
                }
            }
        }*/

        xbinding.KFASmall.minValue = 0
        xbinding.KFASmall.maxValue = 9
        xbinding.KFASmall.value = 5

        initializeDurationNumberPickers(xbinding.sleepDurationHours, xbinding.sleepDurationMinutes)
    }


    private fun getYesterdaysTodaysValueInformation(
        todaysValue: Double?,
        yesterdaysValue: Double?
    ): String {
        val result =
            if (todaysValue != null) {
                if (yesterdaysValue != null) {
                    val sign = if (yesterdaysValue < todaysValue) "+" else "-"
                    todaysValue.toShortString().plus(" / ").plus(sign).plus(" ")
                        .plus(abs(yesterdaysValue - todaysValue).toShortString())
                } else {
                    todaysValue.toShortString().plus(" / ?")
                }
            } else {
                if (yesterdaysValue != null) {
                    "? / ".plus(yesterdaysValue.toShortString())
                } else {
                    "?"
                }
            }

        return result
    }
}


