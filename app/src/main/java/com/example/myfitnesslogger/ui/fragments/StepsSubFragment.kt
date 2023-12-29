package com.example.myfitnesslogger.ui.fragments

import com.example.myfitnesslogger.databinding.InfosFragmentBinding
import com.example.myfitnesslogger.ui.viewmodels.StepsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StepsSubFragment(
    val binding: InfosFragmentBinding,
    val host: BaseFragment,
    val viewModel: StepsViewModel
) {

    fun updateUI() {
        GlobalScope.launch {
            var totalSteps = viewModel.getTodaysTotalSteps()
            host.activity?.runOnUiThread {
                binding.todaysTotalSteps.text = "Todays total steps : ".plus(totalSteps)
            }

            val todaysSteps = viewModel.getTodaysStats()
            val todaysStatsPercents = viewModel.getTodaysStatsPercents()

            host.activity?.runOnUiThread {
                binding.hour0to1.text = getValue(todaysSteps, 0, todaysStatsPercents)
                binding.hour1to2.text = getValue(todaysSteps, 1, todaysStatsPercents)
                binding.hour2to3.text = getValue(todaysSteps, 2, todaysStatsPercents)
                binding.hour3to4.text = getValue(todaysSteps, 3, todaysStatsPercents)
                binding.hour4to5.text = getValue(todaysSteps, 4, todaysStatsPercents)
                binding.hour5to6.text = getValue(todaysSteps, 5, todaysStatsPercents)
                binding.hour6to7.text = getValue(todaysSteps, 6, todaysStatsPercents)
                binding.hour7to8.text = getValue(todaysSteps, 7, todaysStatsPercents)
                binding.hour8to9.text = getValue(todaysSteps, 8, todaysStatsPercents)
                binding.hour9to10.text = getValue(todaysSteps, 9, todaysStatsPercents)
                binding.hour10to11.text = getValue(todaysSteps, 10, todaysStatsPercents)
                binding.hour11to12.text = getValue(todaysSteps, 11, todaysStatsPercents)
                binding.hour12to13.text = getValue(todaysSteps, 12, todaysStatsPercents)
                binding.hour13to14.text = getValue(todaysSteps, 13, todaysStatsPercents)
                binding.hour14to15.text = getValue(todaysSteps, 14, todaysStatsPercents)
                binding.hour15to16.text = getValue(todaysSteps, 15, todaysStatsPercents)
                binding.hour16to17.text = getValue(todaysSteps, 16, todaysStatsPercents)
                binding.hour17to18.text = getValue(todaysSteps, 17, todaysStatsPercents)
                binding.hour18to19.text = getValue(todaysSteps, 18, todaysStatsPercents)
                binding.hour19to20.text = getValue(todaysSteps, 19, todaysStatsPercents)
                binding.hour20to21.text = getValue(todaysSteps, 20, todaysStatsPercents)
                binding.hour21to22.text = getValue(todaysSteps, 21, todaysStatsPercents)
                binding.hour22to23.text = getValue(todaysSteps, 21, todaysStatsPercents)
                binding.hour23to24.text = getValue(todaysSteps, 23, todaysStatsPercents)
            }

            binding.stepsPieView.Percents = todaysStatsPercents
        }
    }

    private fun getValue(todaysSteps: List<String>, index: Int, percents : List<Float>): CharSequence? {
        val prefix = index.toString().plus(" : ").plus(index.toString()).plus(" - ").plus((index+1).toString()).plus(" h : ")

        var result : String = prefix.plus( " ? ")

        if (todaysSteps != null)
        {
            if (index < todaysSteps.count())
            {
                result = prefix.plus(todaysSteps[index])
            }
        }

        if (index < percents.count())
        {
            result = result.plus(" , ").plus("%.2f".format(percents[index])).plus(" %")
        }

        return result
    }

    fun initializeUI() {

    }

}