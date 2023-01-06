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
import com.example.myfitnesslogger2023.ui.baseClasses.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Math.abs

class WeightKFAFragment : BaseFragment() {

    private lateinit var weightAndKFAViewModel: WeightKFAViewModel
    private var _binding: FragmentWeightAndKfaBinding? = null

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

        this.initialize(weightAndKFAViewModel)

        _binding = FragmentWeightAndKfaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // initialize fragment controls
        initializeNumberPickers()
        initializeSendButton()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        this.reInitializeLabels()
        this.binding.SendButton.isEnabled = this.weightAndKFAViewModel.canSendData()
    }

    private fun getYesterdaysTodaysWeightInformation() : String {
        val todaysWeight = this.weightAndKFAViewModel.getPastWeight(0, activity)
        val yesterdaysWeight = this.weightAndKFAViewModel.getPastWeight(-1, activity)

        return getYesterdaysTodaysValueInformation(todaysWeight, yesterdaysWeight)
    }

    private fun getYesterdaysTodaysKFAInformation() : String
    {
        val todaysKFA = this.weightAndKFAViewModel.getPastKFA(0, activity)
        val yesterdaysKFA = this.weightAndKFAViewModel.getPastKFA(-1, activity)

        return getYesterdaysTodaysValueInformation(todaysKFA, yesterdaysKFA)
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

    private fun reInitializeLabels() {
        GlobalScope.launch {
            val yesterdaysTodaysWeightInformation = this@WeightKFAFragment.getYesterdaysTodaysWeightInformation()
            val yesterdaysTodaysKFAInformation = this@WeightKFAFragment.getYesterdaysTodaysKFAInformation()

            activity?.runOnUiThread {
                this@WeightKFAFragment.initializeALabel(this@WeightKFAFragment.binding.weightLabel, R.string.gewicht, yesterdaysTodaysWeightInformation)
                this@WeightKFAFragment.initializeALabel(this@WeightKFAFragment.binding.kfaLabel, R.string.kfa, yesterdaysTodaysKFAInformation)
            }
        }
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
        binding.KFASmall.value = 5    }

    private fun initializeSendButton()
    {
        binding.SendButton.setOnClickListener {
            keyboardService.hideKeyboard()
            binding.weightGreat.clearFocus()
            binding.weightSmall.clearFocus()
            binding.KFAGreat.clearFocus()
            binding.KFASmall.clearFocus()

            GlobalScope.launch {
                activity?.runOnUiThread {
                    binding.circularProgress.visibility = VISIBLE
                }

                weightAndKFAViewModel.sendData(
                    binding.weightGreat.value,
                    binding.weightSmall.value,
                    binding.KFAGreat.value,
                    binding.KFASmall.value,
                    binding.KFASmall.isVisible,
                    activity)

                activity?.runOnUiThread {
                    binding.circularProgress.visibility = INVISIBLE
                    this@WeightKFAFragment.reInitializeLabels()
                }
            }
        }
    }
}


