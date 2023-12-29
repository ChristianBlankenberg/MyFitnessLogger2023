package com.example.myfitnesslogger.ui.viewmodels

import android.widget.NumberPicker
import androidx.fragment.app.FragmentActivity
import com.example.myfitnesslogger.businesslogic.*

class TrainingFragmentViewModel() : BaseViewModel() {

    init {
        initialize()
    }

    private fun initialize() {

    }

    fun initializeNumberPickers(
        hourNumberPicker: NumberPicker,
        minuteNumberPicker: NumberPicker,
        calories: NumberPicker,
    ) {
        hourNumberPicker.minValue = 0
        hourNumberPicker.maxValue = 8
        hourNumberPicker.value = 2

        minuteNumberPicker.minValue = 0
        minuteNumberPicker.maxValue = 59
        minuteNumberPicker.value = 5

        calories.minValue = 1
        calories.maxValue = 2000
        calories.value = 1000
    }

/*
    fun sendData(hours: Int, minutes: Int, calories: Int, remarks: String) {
        val url = this.GetUrl()
        dataGate.sendTrainingMinutes(url, hours * 60 + minutes)
        dataGate.sendCalories(url, calories)
        dataGate.sendRemarks(url, remarks)
        dataGate.sendTime(url)
    }
*/


    fun addData(hours: Int, minutes: Int, calories: Int) {
        val url = this.GetUrl(infoType.training)
        var combinedTrainingMinutesAndCalories = trainingDataGate.getCombinedTrainingMinutesAndCalories(url)

        combinedTrainingMinutesAndCalories=combinedTrainingMinutesAndCalories.removeSuffix(";")

        var mins : Int = 0
        var cals : Int = 0

        if (combinedTrainingMinutesAndCalories.length > 0) {
            val minsStr = this.getIndexedString(combinedTrainingMinutesAndCalories, 0)
            val calsStr = this.getIndexedString(combinedTrainingMinutesAndCalories, 1)
            try {
                mins = minsStr.toInt()
                cals = calsStr.toInt()
            }
            catch(e : Exception)
            {
                mins = 0
                cals = 0
            }
        }

        val newMinutes = mins + hours * 60 + minutes
        val newCalories = cals + calories

        trainingDataGate.sendCombinedTrainingMinutesAndCalories(url,
            newMinutes,
            newCalories)

        trainingDataGate.sendTodaysDate(this.GetUrl(infoType.training), this.getTimeddmMyyyy())

        sharedPrefGate.setValue(this.activity, "minutes".plus(this.getSuffix(0)), newMinutes.toString())
        sharedPrefGate.setValue(this.activity, "calories".plus(this.getSuffix(0)), newCalories.toString())

        trainingDataGate.sendTime(url)
    }

    fun getTodaysDuration(): String {
        return this.getCachedValue(dataSetType.TrainingData, "minutes".plus(this.getSuffix(0)))
    }

    fun getTodaysCalories(): String {
        return this.getCachedValue(dataSetType.TrainingData, "calories".plus(this.getSuffix(0)))
    }
}