package com.example.myfitnesslogger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger.R
import com.example.myfitnesslogger.businesslogic.infoType
import com.example.myfitnesslogger.databinding.TrainingFragmentBinding
import com.example.myfitnesslogger.services.keyboardService
import com.example.myfitnesslogger.ui.viewmodels.TrainingFragmentViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrainingFragment : BaseFragment() {

    companion object {
        fun newInstance() =TrainingFragment()
    }

    private lateinit var viewModel: TrainingFragmentViewModel
    private lateinit var binding: TrainingFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        reInitializeLabels()

        this.binding.AddButton.isEnabled = this.isConnectionUrlDefined(infoType.training)
    }

    private fun reInitializeLabels() {
        this.initializeALabel(this.binding.durationLabel, R.string.duration, this.viewModel.getTodaysDuration())
        this.initializeALabel(this.binding.caloriesLabel, R.string.calories, this.viewModel.getTodaysCalories() )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = TrainingFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(TrainingFragmentViewModel::class.java)
        this.initialize(viewModel)

        viewModel.initializeNumberPickers(
            binding.hours,
            binding.minutes,
            binding.calories)

        binding.AddButton.setOnClickListener {
            keyboardService.hideKeyboard()
            binding.hours.clearFocus()
            binding.minutes.clearFocus()
            binding.calories.clearFocus()

            GlobalScope.launch {
                activity?.runOnUiThread {
                    binding.circularProgress.visibility = View.VISIBLE
                }

                viewModel.addData(
                    binding.hours.value,
                    binding.minutes.value,
                    binding.calories.value)

                activity?.runOnUiThread {
                    binding.circularProgress.visibility = View.INVISIBLE
                    this@TrainingFragment.reInitializeLabels()
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}