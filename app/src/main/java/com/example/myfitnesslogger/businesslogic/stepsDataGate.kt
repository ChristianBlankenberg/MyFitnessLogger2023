package com.example.mystepcounter3.businessLogic

import com.example.myfitnesslogger.businesslogic.baseDataGate
import com.example.myfitnesslogger.businesslogic.getIntValueString
import com.example.myfitnesslogger.businesslogic.httpRequestService
import java.util.*

object stepsDataGate : baseDataGate() {

    private val numberOfMeasureMentRow: Int = 3
    private val numberOfStepsRow: Int = 2
    private val todaysDateRow: Int = 1

    fun sendStepCounterValueAndTimehhmm(
        url: String,
        stepCounterValue: Int,
        timeHHmm: String,
        numberOfMeasurement: Int
    ): Boolean {
        val col = getDayCol()
        val row = numberOfMeasurement + numberOfMeasureMentRow
        return sendValue(
            url,
            this.getDataSheet(),
            stepCounterValue.toString().plus(";").plus(timeHHmm),
            col,
            row
        )
    }

    fun sendTodaysDate(url: String, date: String): Boolean {
        val col = getDayCol()
        val row = todaysDateRow
        return sendValue(url, this.getDataSheet(), date, col, row)
    }

    fun sendNumberOfMeasurementValue(url: String, numberOfMeasurement: Int): Boolean {
        val col = getDayCol()
        val row = numberOfMeasureMentRow
        return sendValue(url, this.getDataSheet(), getIntValueString(numberOfMeasurement), col, row)
    }

    fun getTodaysMeasurement(url: String, measurementNumber: Int): String {
        val col = getDayCol()
        val row = measurementNumber + numberOfMeasureMentRow
        return this.getValue(url, this.getDataSheet(), col, row)
    }

    fun getTodaysStats(url: String): String {
        val col = 16
        val row = 25
        return this.getValue(url, this.getTodaysSheet(), col, row)
    }

    fun getTodaysTotalSteps(url: String): String {
        return this.getValue(url, this.getTodaysSheet(), 2, 5)
    }

    fun getNumberOfMeasurementValue(url: String): Int {
        val col = getDayCol()
        val row = numberOfMeasureMentRow
        return this.getValue(url, this.getDataSheet(), col, row).toIntOrNull() ?: -1
    }

    fun getNumberOfTodaysSteps(url: String): Int {
        val col = getDayCol()
        val row = numberOfStepsRow
        return this.getValue(url, this.getDataSheet(), col, row).toIntOrNull() ?: -1
    }

    private fun getDataSheet(): String {
        return "data"
    }

    private fun getTodaysSheet(): String {
        return "today"
    }

    private fun getAnyDaysSheet(): String {
        return "anyday"
    }

    private fun getDayCol(): Int {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        return ((month - 1) * 31 + day)
    }

    private fun getMonthRow(month: Int): Int {
        return month + 1
    }
}