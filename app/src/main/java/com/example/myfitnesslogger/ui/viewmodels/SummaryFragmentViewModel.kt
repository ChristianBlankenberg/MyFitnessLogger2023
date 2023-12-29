package com.example.myfitnesslogger.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import com.example.myfitnesslogger.businesslogic.infoType
import com.example.myfitnesslogger.businesslogic.trainingDataGate
import java.lang.Exception

class SummaryFragmentViewModel() : BaseViewModel() {
    private var todaysData : String = ""
    private var monthData : String = ""
    private var prevMonthData : String = ""

    fun getAsRoundedString(value : String) : String
    {
        try {
            var doubleValue = value.replace(",", ".").toDouble()
            doubleValue = Math.round(doubleValue  * 100) / 100.0

            return doubleValue.toString()
        }
        catch(ex : Exception)
        {}

        return value
    }

    fun getTodaysWeight(): String {
        return this.getIndexedString(this.todaysData, 0)
    }

    fun getTodaysKFA(): String {
        return this.getIndexedString(this.todaysData, 1)
    }

    fun getTodaysTrainingMinutes(): String {
        return this.getIndexedString(this.todaysData, 2)
    }

    fun getTodaysTrainingTime(): String {
        return trainingDataGate.getTime(this.GetUrl(infoType.training))
    }

    fun getTodaysCalories(): String {
        return this.getIndexedString(this.todaysData, 3)
    }

    fun getPrevMonthAverageWeight(): String {
        return this.getIndexedString(this.prevMonthData, 0)
    }

    fun getPrevMonthAverageKFA(): String {
        return this.getIndexedString(this.prevMonthData, 1)
    }

    fun getPrevMonthTrainingHours(): String {
        return this.getIndexedString(this.prevMonthData, 2)
    }

    fun getPrevMonthMegaCalories()    : String {
        return this.getIndexedString(this.prevMonthData, 3)
    }

    fun getMonthAverageWeight(): String {
        return this.getIndexedString(this.monthData, 0)
    }

    fun getMonthAverageKFA(): String {
        return this.getIndexedString(this.monthData, 1)
    }

    fun getMonthTrainingHours(): String {
        return this.getIndexedString(this.monthData, 2)
    }

    fun getMonthMegaCalories()    : String {
        return this.getIndexedString(this.monthData, 3)
    }

    fun getTodaysData() {
        todaysData = trainingDataGate.getCombinedTodaysData(this.GetUrl(infoType.training))
    }

    fun getMonthsData() {
        monthData = trainingDataGate.getCombinedMonthData(this.GetUrl(infoType.training))
    }

    fun getPrevMonthsData() {
        prevMonthData = trainingDataGate.getCombinedPrevMonthData(this.GetUrl(infoType.training))
    }
}