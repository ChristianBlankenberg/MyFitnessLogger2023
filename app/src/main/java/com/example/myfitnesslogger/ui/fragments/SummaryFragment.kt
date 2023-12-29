package com.example.myfitnesslogger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnesslogger.businesslogic.infoType
import com.example.myfitnesslogger.databinding.SummaryFragmentBinding
import com.example.myfitnesslogger.ui.viewmodels.SummaryFragmentViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SummaryFragment : BaseFragment() {
    companion object {
        fun newInstance() = SummaryFragment()
    }

    private lateinit var viewModel: SummaryFragmentViewModel
    private lateinit var binding: SummaryFragmentBinding

    override fun onResume() {
        super.onResume()
        this.binding.getDayDataButton.isEnabled = this.isConnectionUrlDefined(infoType.training)
        this.binding.getMonthDataButton.isEnabled = this.isConnectionUrlDefined(infoType.training)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = SummaryFragmentBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(SummaryFragmentViewModel::class.java)
        this.initialize(viewModel)

        binding.getMonthDataButton.setOnClickListener {
            GlobalScope.launch {
                activity?.runOnUiThread {
                    binding.circularMonthProgress.visibility = View.VISIBLE
                }

                viewModel.getMonthsData()

                val monthAverageWeight = viewModel.getAsRoundedString(viewModel.getMonthAverageWeight())
                val monthAverageKFA = viewModel.getAsRoundedString(viewModel.getMonthAverageKFA())
                val monthTrainingHours = viewModel.getAsRoundedString(viewModel.getMonthTrainingHours())
                val monthMegaCalories = viewModel.getAsRoundedString(viewModel.getMonthMegaCalories())

                viewModel.getPrevMonthsData()

                val previousMonthAverageWeight = viewModel.getAsRoundedString(viewModel.getPrevMonthAverageWeight())
                val previousMonthAverageKFA = viewModel.getAsRoundedString(viewModel.getPrevMonthAverageKFA())
                val previousMonthTrainingHours = viewModel.getAsRoundedString(viewModel.getPrevMonthTrainingHours())
                val previousMonthMegaCalories = viewModel.getAsRoundedString(viewModel.getPrevMonthMegaCalories())

                activity?.runOnUiThread {
                    binding.monthAverageWeight.text = monthAverageWeight.plus(" ( ").plus(previousMonthAverageWeight).plus(" )")
                    binding.monthAverageKFA.text = monthAverageKFA.plus(" ( ").plus(previousMonthAverageKFA).plus(" )")
                    binding.monthTrainingHours.text = monthTrainingHours.plus(" ( ").plus(previousMonthTrainingHours).plus(" )")
                    binding.monthMegaCalories.text = monthMegaCalories.plus(" ( ").plus(previousMonthMegaCalories).plus(" )")

                    binding.circularMonthProgress.visibility = View.INVISIBLE
                }
            }
        }

        binding.getDayDataButton.setOnClickListener {
            GlobalScope.launch {
                activity?.runOnUiThread {
                    binding.circularDayProgress.visibility = View.VISIBLE
                }

                viewModel.getTodaysData();

                val todaysWeight = viewModel.getAsRoundedString(viewModel.getTodaysWeight())
                val todaysKFA = viewModel.getAsRoundedString(viewModel.getTodaysKFA())
                val todaysTrainingHours = viewModel.getAsRoundedString(viewModel.getTodaysTrainingMinutes())
                val todaysMegaCalories = viewModel.getAsRoundedString(viewModel.getTodaysCalories())
                val todaysTrainingTime = viewModel.getTodaysTrainingTime()

                activity?.runOnUiThread {
                    binding.currentWeight.text = todaysWeight
                    binding.currentKFA.text = todaysKFA
                    binding.currentTrainingHours.text = todaysTrainingHours
                    binding.currentCalories.text = todaysMegaCalories
                    binding.currentTrainingTime.text = todaysTrainingTime

                    binding.circularDayProgress.visibility = View.INVISIBLE
                }
            }
        }

        this.initializePastValues()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializePastValues() {
        this.binding.lastWeight1.text = this@SummaryFragment.viewModel.getPastWeight(-1)
        this.binding.lastKFA1.text = this@SummaryFragment.viewModel.getPastKFA(-1)

        this.binding.lastWeight2.text = this@SummaryFragment.viewModel.getPastWeight(-2)
        this.binding.lastKFA2.text = this@SummaryFragment.viewModel.getPastKFA(-2)

        this.binding.lastWeight3.text = this@SummaryFragment.viewModel.getPastWeight(-3)
        this.binding.lastKFA3.text = this@SummaryFragment.viewModel.getPastKFA(-3)

        this.binding.lastWeight4.text = this@SummaryFragment.viewModel.getPastWeight(-4)
        this.binding.lastKFA4.text = this@SummaryFragment.viewModel.getPastKFA(-4)

        this.binding.lastWeight5.text = this@SummaryFragment.viewModel.getPastWeight(-5)
        this.binding.lastKFA5.text = this@SummaryFragment.viewModel.getPastKFA(-5)

        this.binding.lastWeight6.text = this@SummaryFragment.viewModel.getPastWeight(-6)
        this.binding.lastKFA6.text = this@SummaryFragment.viewModel.getPastKFA(-6)

        this.binding.lastWeight7.text = this@SummaryFragment.viewModel.getPastWeight(-7)
        this.binding.lastKFA7.text = this@SummaryFragment.viewModel.getPastKFA(-7)
    }
}