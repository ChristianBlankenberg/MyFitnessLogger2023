package com.example.myfitnesslogger2023.utils

class mathFunctions {

    fun getPreAndPastCommaValue(number : Double) : Pair<Int, Int>
    {
        val preCommaValue = number.toInt()
        val pastComma = ((number - preCommaValue) * 10).toInt()
        return  Pair(preCommaValue, pastComma)
    }
}