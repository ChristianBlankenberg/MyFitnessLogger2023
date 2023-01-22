package com.example.myfitnesslogger2023.ui.baseClasses

import android.widget.NumberPicker

abstract class ActivitySendInfoBaseFragment() : SendInfoBaseFragment() {

    fun initializeDistanceActivityControls(
        distanceKmNP : NumberPicker,
        distancemNP : NumberPicker,
        durationHrNP : NumberPicker,
        durationmNP : NumberPicker,
        distancekmMax : Int,
        durationHrMax : Int,
        defaultValueDistance : Int,
        defaultValueDurationHr : Int
        )
    {
        distanceKmNP.minValue = 0
        distanceKmNP.maxValue = distancekmMax
        distanceKmNP.value = defaultValueDistance

        distancemNP.minValue = 0
        distancemNP.maxValue = 99
        distancemNP.value = 0

        durationHrNP.minValue = 0
        durationHrNP.maxValue = durationHrMax
        durationHrNP.minValue = defaultValueDurationHr

        durationmNP.minValue = 0
        durationmNP.maxValue = 59
        durationmNP.minValue = 0
    }
}