package com.CBPrograms.myfitnesslogger2023.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.CBPrograms.myfitnesslogger2023.utils.informationType
import com.CBPrograms.myfitnesslogger2023.R
import com.CBPrograms.myfitnesslogger2023.databinding.FragmentJoggingBinding
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class JoggingFragment : TabulatorChildFragment() {

    private var binding: FragmentJoggingBinding? = null

    private lateinit var joggingViewModel: JoggingViewModel

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
        GlobalScope.launch {
            val activityDetails = joggingViewModel.getActivity(activity)

            val joggingDistance = activityDetails[2]
            val durationHoursAndMinutes = mathFunctions.getHoursAndMinutes(activityDetails[0].toIntOrNull() ?: 0)
            val calories = activityDetails[1]

            activity?.runOnUiThread {
                this@JoggingFragment.initializeALabel(
                    this@JoggingFragment.binding?.distanceLabel,
                    R.string.distance,
                    joggingDistance
                )
            }

            activity?.runOnUiThread {
                this@JoggingFragment.initializeALabel(
                    this@JoggingFragment.binding?.durationLabel,
                    R.string.duration,
                    durationHoursAndMinutes.first.plus(":").plus(durationHoursAndMinutes.second)
                )
            }

            activity?.runOnUiThread {
                this@JoggingFragment.initializeALabel(
                    this@JoggingFragment.binding?.caloriesLabel,
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