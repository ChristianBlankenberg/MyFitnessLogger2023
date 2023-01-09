package com.example.myfitnesslogger2023.ui.weightAndKfa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger.services.keyboardService
import com.example.myfitnesslogger2023.R
import com.example.myfitnesslogger2023.databinding.FragmentWeightAndKfaBinding
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment
import com.example.myfitnesslogger2023.utils.mathFunctions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Math.abs

class WeightKFAFragment : SendInfoBaseFragment() {

    private lateinit var weightAndKFAViewModel: WeightKFAViewModel
    private var _binding: FragmentWeightAndKfaBinding? = null
    private val mathFunctions = mathFunctions()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weightAndKFAViewModel =
            ViewModelProvider(this).get(WeightKFAViewModel::class.java)
        weightAndKFAViewModel.initialize(this.requireActivity());

        _binding = FragmentWeightAndKfaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        this.initialize(weightAndKFAViewModel, binding.SendButton, binding.circularProgress)

        // initialize fragment controls
        initializeNumberPickers()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getYesterdaysTodaysWeightInformation(todaysWeight : Double?) : String {
        val yesterdaysWeight = this.weightAndKFAViewModel.getPastWeight(-1, activity)

        return getYesterdaysTodaysValueInformation(todaysWeight, yesterdaysWeight)
    }

    private fun getYesterdaysTodaysKFAInformation(todaysKFA : Double?) : String
    {
        val yesterdaysKFA = this.weightAndKFAViewModel.getPastKFA(-1, activity)

        return getYesterdaysTodaysValueInformation(todaysKFA, yesterdaysKFA)
    }

    override fun reInitializeLabels() {
        GlobalScope.launch {
            val todaysWeight = this@WeightKFAFragment.weightAndKFAViewModel.getPastWeight(0, activity)
            val todaysKFA = this@WeightKFAFragment.weightAndKFAViewModel.getPastKFA(0, activity)

            val yesterdaysTodaysWeightInformation = this@WeightKFAFragment.getYesterdaysTodaysWeightInformation(todaysWeight)
            val yesterdaysTodaysKFAInformation = this@WeightKFAFragment.getYesterdaysTodaysKFAInformation(todaysKFA)

            if (_binding != null) {
                activity?.runOnUiThread {
                    this@WeightKFAFragment.initializeALabel(
                        this@WeightKFAFragment.binding.weightLabel,
                        R.string.gewicht,
                        yesterdaysTodaysWeightInformation
                    )
                    this@WeightKFAFragment.initializeALabel(
                        this@WeightKFAFragment.binding.kfaLabel,
                        R.string.kfa,
                        yesterdaysTodaysKFAInformation
                    )

                    val todaysWeightPreAndPastComma = mathFunctions.getPreAndPastCommaValue(todaysWeight ?: 0.0)
                    val todaysKFAComma = mathFunctions.getPreAndPastCommaValue(todaysKFA ?: 0.0)

                    this@WeightKFAFragment.binding.weightGreat.value = todaysWeightPreAndPastComma.first
                    this@WeightKFAFragment.binding.weightSmall.value = todaysWeightPreAndPastComma.second

                    this@WeightKFAFragment.binding.KFAGreat.value = todaysKFAComma.first
                    this@WeightKFAFragment.binding.KFASmall.value = todaysKFAComma.second
                }
            }
        }
    }

    override fun sendPreAction() {
        binding.weightGreat.clearFocus()
        binding.weightSmall.clearFocus()
        binding.KFAGreat.clearFocus()
        binding.KFASmall.clearFocus()
    }

    override fun sendAction() {
        weightAndKFAViewModel.sendData(
            binding.weightGreat.value,
            binding.weightSmall.value,
            binding.KFAGreat.value,
            binding.KFASmall.value,
            binding.KFASmall.isVisible,
            activity)
    }

    private fun initializeNumberPickers()
    {
        binding.weightGreat.minValue = 72
        binding.weightGreat.maxValue = 85
        binding.weightGreat.value = 78

        binding.weightSmall.minValue = 0
        binding.weightSmall.maxValue = 9
        binding.weightSmall.value = 5

        binding.KFAGreat.minValue = 17
        binding.KFAGreat.maxValue = 25
        binding.KFAGreat.value = 21

        binding.KFAGreat.setFormatter {
            when (it) {
                17 -> {
                    binding.KFASmall.isVisible = false
                    binding.kfaComma.isVisible = false
                    "-"
                }
                else -> {
                    binding.KFASmall.isVisible = true
                    binding.kfaComma.isVisible = true
                    it.toString()
                }
            }
        }

        binding.KFASmall.minValue = 0
        binding.KFASmall.maxValue = 9
        binding.KFASmall.value = 5
    }


    private fun getYesterdaysTodaysValueInformation(todaysValue : Double?, yesterdaysValue : Double?) : String {
        val result =
            if (todaysValue != null)
            {
                if (yesterdaysValue != null)
                {
                    val sign = if (yesterdaysValue < todaysValue) "+" else "-"
                    todaysValue.toShortString().plus(" / ").plus(sign).plus(" ").plus(abs(yesterdaysValue-todaysValue).toShortString())
                }
                else
                {
                    todaysValue.toShortString().plus(" / ?")
                }
            }
            else
            {
                if (yesterdaysValue != null)
                {
                    "? / ".plus(yesterdaysValue.toShortString())
                }
                else {
                    "?"
                }
            }

        return result
    }
}


