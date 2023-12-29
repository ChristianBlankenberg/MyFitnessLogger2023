package com.example.myfitnesslogger.businesslogic

import java.net.URLDecoder
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

object trainingDataGate : baseDataGate() {
    private val montRowCorrector : Int = 1

    fun sendWeight(url: String, weight: Double): Boolean {
        val col = 1
        val row = getDayRow()
        return sendValue(url, this.getDataSheet(), getDoubleValueString(weight), col, row)
    }

    fun getWeight(url: String): Double {
        val col = 1
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row).toDoubleOrNull() ?: 0.0
    }

    fun sendKFA(url: String, kfa: Double): Boolean {
        val col = 2
        val row = getDayRow()
        return sendValue(url, this.getDataSheet(), getDoubleValueString(kfa), col, row)
    }

    fun getKFA(url: String): Double {
        val col = 2
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row).toDoubleOrNull() ?: 0.0
    }

    fun sendTodaysDate(url : String, date : String): Boolean {
        val col = 8
        val row = getDayRow()
        return sendValue(url, this.getDataSheet(), date, col, row)
    }

    fun sendCombinedWeightAndKFA(url: String, weight: String, kfa: String): Boolean {
        val col = 9
        val row = getDayRow()
        return sendValue(url, this.getDataSheet(), weight.plus(";").plus(kfa).plus(";"), col, row)
    }

    fun getTrainingMinutes(url: String): Int {
        val col = 3
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row).toIntOrNull() ?: 0
    }

    fun sendTrainingMinutes(url: String, trainingMinutes: Int): Boolean {
        val col = 3
        val row = getDayRow()
        return sendValue(url, this.getDataSheet(), trainingMinutes.toString(), col, row)
    }

    fun getCalories(url: String): Int {
        val col = 4
        val row = getDayRow()
        val value = this.getValue(url, this.getDataSheet(), col, row)
        return value.toIntOrNull() ?: 0
    }

    fun sendCalories(url: String, calories: Int): Boolean {
        val col = 4
        val row = getDayRow()
        return sendValue(url, this.getDataSheet(), calories.toString(), col, row)
    }

    fun sendCombinedTrainingMinutesAndCalories(
        url: String,
        trainingMinutes: Int,
        calories: Int
    ): Boolean {
        val col = 10
        val row = getDayRow()
        return sendValue(url, this.getDataSheet(), getIntValueString(trainingMinutes).plus(";").plus(getIntValueString(calories)).plus(";")
            , col, row)
    }

    fun getCombinedTrainingMinutesAndCalories(url: String): String {
        val col = 10
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row)
    }

    fun getCombinedTodaysData(url: String): String {
        val col = 11
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row)
    }

    fun getCombinedMonthData(url: String): String {
        val col = 6
        val row = getMonthRow()
        return this.getValue(url, this.getDataEvaluationSheet(), col, row)
    }

    fun getCombinedPrevMonthData(url: String): String {
        val col = 6
        val row = getMonthRow() - 1
        return this.getValue(url, this.getDataEvaluationSheet(), col, row)
    }

    fun sendRemarks(url: String, remarks: String): Boolean {
        val col = 6
        val row = getDayRow()
        return sendValue(url, this.getDataSheet(), URLEncoder.encode(remarks, "UTF-8"), col, row)
    }

    fun getRemarks(url: String): String {
        val col = 6
        val row = getDayRow()
        return URLDecoder.decode(this.getValue(url, this.getDataSheet(), col, row), "UTF-8")
    }

    fun getTime(url: String): String {
        val col = 5
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row).replace("-", ":").replace("!", "")
    }

    fun sendTime(url: String): Boolean {
        val col = 5
        val row = getDayRow()

        val timeZone = TimeZone.getTimeZone("Europe/Berlin")
        val calendar = Calendar.getInstance(timeZone)
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
        simpleDateFormat.timeZone = timeZone;

        var time = "!".plus(simpleDateFormat.format(Calendar.getInstance().time).replace(":", "-"))

        return sendValue(url, this.getDataSheet(), time, col, row)
    }

    private fun getDataSheet(): String {
        return "data"
    }

    private fun getDataEvaluationSheet(): String {
        return "dataevaluation"
    }

    private fun getDayRow(): Int {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        return (month - 1) * 33 + 1 + day
    }

    private fun getMonthRow() : Int
    {
        return this.getMonthRow(Calendar.getInstance().get(Calendar.MONTH) + 1)
    }

    private fun getMonthRow(month : Int): Int {
        return month + 1 + montRowCorrector
    }

}