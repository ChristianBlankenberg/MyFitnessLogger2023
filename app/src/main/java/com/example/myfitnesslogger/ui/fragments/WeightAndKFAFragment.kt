package com.example.myfitnesslogger.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.myfitnesslogger.R
import com.example.myfitnesslogger.ui.viewmodels.WeightAndKFAViewModel
import com.example.myfitnesslogger.databinding.WeightkfaFragmentBinding
import com.example.myfitnesslogger.services.keyboardService
import com.example.myfitnesslogger.businesslogic.infoType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.abs

class WeightAndKFAFragment : BaseFragment() {

    companion object {
        fun newInstance() = WeightAndKFAFragment()
    }

    private lateinit var viewModel: WeightAndKFAViewModel
    private lateinit var binding: WeightkfaFragmentBinding

    override fun onResume() {
        super.onResume()

        this.reInitializeLabels()
        this.binding.SendButton.isEnabled = this.isConnectionUrlDefined(infoType.training)
    }

    private fun getYesterdaysTodaysWeightInformation() : String {
        val todaysWeight = this.viewModel.getTodaysWeight().toDoubleOrNull();
        val yesterdaysWeight = this.viewModel.getPastWeight(-1).toDoubleOrNull();

        return getYesterdaysTodaysValueInformation(todaysWeight, yesterdaysWeight)
    }

    private fun getYesterdaysTodaysKFAInformation() : String
    {
        val todaysKFA = this.viewModel.getTodaysKFA().toDoubleOrNull();
        val yesterdaysKFA = this.viewModel.getPastKFA(-1).toDoubleOrNull();

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
        this.initializeALabel(this.binding.weightLabel, R.string.gewicht, this.getYesterdaysTodaysWeightInformation())
        this.initializeALabel(this.binding.kfaLabel, R.string.kfa, this.getYesterdaysTodaysKFAInformation())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = WeightkfaFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(WeightAndKFAViewModel::class.java)
        this.initialize(viewModel)

        viewModel.initializeNumberPickers(
            binding.weightGreat,
            binding.weightSmall,
            binding.KFAGreat,
            binding.KFASmall,
            {
                binding.KFASmall.isVisible = false
                binding.kfaComma.isVisible = false
            },
            {
                binding.KFASmall.isVisible = true
                binding.kfaComma.isVisible = true
            }
        )

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

                viewModel.sendData(
                    binding.weightGreat.value,
                    binding.weightSmall.value,
                    binding.KFAGreat.value,
                    binding.KFASmall.value,
                    binding.KFASmall.isVisible)

                activity?.runOnUiThread {
                    binding.circularProgress.visibility = INVISIBLE
                    this@WeightAndKFAFragment.reInitializeLabels()
                }
            }
        }

        return binding.root
    }
}

private fun Double.toShortString(): String {
    return (Math.round(this * 100) / 100.0).toString()
}

