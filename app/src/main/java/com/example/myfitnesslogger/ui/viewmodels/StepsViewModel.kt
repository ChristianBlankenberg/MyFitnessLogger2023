package com.example.myfitnesslogger.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import com.example.myfitnesslogger.businesslogic.infoType
import com.example.mystepcounter3.businessLogic.stepsDataGate

class StepsViewModel() : BaseViewModel() {

    fun getTodaysTotalSteps() : String
    {
        return stepsDataGate.getTodaysTotalSteps(this.GetUrl(infoType.steps))
    }

    fun getTodaysStats() : List<String>
    {
        val todaysStats = stepsDataGate.getTodaysStats(this.GetUrl(infoType.steps))
        return todaysStats.split(";")
    }

    fun getTodaysStatsPercents() : List<Float>
    {
        val todaysStats = stepsDataGate.getTodaysStats(this.GetUrl(infoType.steps))
        val hourStepsString = todaysStats.split(";")

        var stepsSum : Float = 0.0f
        val hourStepsFl : MutableList<Float> = mutableListOf<Float>()
        for(idx in 0 .. hourStepsString.count()-1)
        {
            var steps = hourStepsString[idx].toFloat()
            hourStepsFl.add(steps)
            stepsSum += steps
        }

        for(idx in 0 .. hourStepsFl.count()-1) {
            hourStepsFl[idx] = hourStepsFl[idx] / stepsSum * 100.0f
        }

        return hourStepsFl
    }
}