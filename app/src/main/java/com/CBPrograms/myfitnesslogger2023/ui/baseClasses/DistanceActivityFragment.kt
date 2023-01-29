package com.CBPrograms.myfitnesslogger2023.ui.baseClasses

import android.widget.NumberPicker
import android.widget.TextView
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.ui.activity.SendActivityBaseViewModel
import com.CBPrograms.myfitnesslogger2023.ui.activity.TabulatorChildFragment
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class DistanceActivityFragment : TabulatorChildFragment() {

    protected lateinit var sendActivityBaseViewModel : SendActivityBaseViewModel

    abstract fun getNumberPickerDataControls() : ArrayList<NumberPicker?>

    override fun sendPreAction() {
        getNumberPickerDataControls().forEach { it?.clearFocus() }
    }

    override fun sendAction() {
        var numberPickerDataControls = getNumberPickerDataControls()

        sendActivityBaseViewModel.sendActivity(
            numberPickerDataControls.get(0)?.value ?: 0,
            numberPickerDataControls.get(1)?.value ?: 0,
            numberPickerDataControls.get(2)?.value ?: 0,
            numberPickerDataControls.get(3)?.value ?: 0,
            numberPickerDataControls.get(4)?.value ?: 0,
        );
    }

    abstract fun getReinitializeLabels() : Triple<TextView? , TextView?, TextView?>;

    override fun reInitializeLabels() {
        GlobalScope.launch {
            val reinitializeLabels = getReinitializeLabels()

            val activityDetails = sendActivityBaseViewModel.getActivity(activity)

            val distance = activityDetails[2]
            val durationHoursAndMinutes = mathFunctions.getHoursAndMinutes(activityDetails[0].toIntOrNull() ?: 0)
            val calories = activityDetails[1]

            activity?.runOnUiThread {
                this@DistanceActivityFragment.initializeALabel(
                    reinitializeLabels.first,
                    R.string.distance,
                    distance
                )
            }

            activity?.runOnUiThread {
                this@DistanceActivityFragment.initializeALabel(
                    reinitializeLabels.second,
                    R.string.duration,
                    durationHoursAndMinutes.first.plus(":").plus(durationHoursAndMinutes.second)
                )
            }

            activity?.runOnUiThread {
                this@DistanceActivityFragment.initializeALabel(
                    reinitializeLabels.third,
                    R.string.calories,
                    calories
                )
            }

            /*
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
                    todaysSleepDurationHours + ":" + todaysSleepDurationMinutes
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
                    todaysSleepDurationHours.toIntOrNull() ?: 8
                this@WeightKFAFragment.binding?.sleepDurationMinutes?.value =
                    todaysSleepDurationMinutes.toIntOrNull() ?: 0
            }*/
        }
    }

}