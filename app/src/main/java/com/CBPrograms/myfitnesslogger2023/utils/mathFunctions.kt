package com.CBPrograms.myfitnesslogger2023.utils

object mathFunctions {

    fun getDoubleValue(preCommaValue : Int, pastCommaValue : Int) : Double
    {
        var pastCommaDoubleValue : Double = pastCommaValue * 1.0
        while(pastCommaDoubleValue > 1.0)
        {
            pastCommaDoubleValue = pastCommaDoubleValue.div(10.0)
        }

        return preCommaValue.toDouble() + pastCommaDoubleValue
    }

    fun getPreAndPastCommaValue(number : Double) : Pair<Int, Int>
    {
        val preCommaValue = number.toInt()
        val pastComma = ((number - preCommaValue) * 10).toInt()
        return  Pair(preCommaValue, pastComma)
    }

    fun getHoursAndMinutes(minutes : Int) : Pair<String, String>
    {
        val hours = (minutes.toInt().div(60))
        val minutes = "%02d".format (minutes - hours * 60)

        return Pair((hours).toString(), minutes)
    }

    fun getMinutes(hours: Int, minutes: Int): Int {
        return hours * 60 + minutes
    }
}