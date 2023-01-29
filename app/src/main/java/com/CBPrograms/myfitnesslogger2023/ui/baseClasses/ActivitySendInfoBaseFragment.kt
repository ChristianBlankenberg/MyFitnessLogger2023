package com.CBPrograms.myfitnesslogger2023.ui.baseClasses

import android.widget.NumberPicker

abstract class ActivitySendInfoBaseFragment() : SendInfoBaseFragment() {

    fun initializeDistanceActivityControls(
        distanceKmNP : NumberPicker,
        distancemNP : NumberPicker,
        durationHrNP : NumberPicker,
        durationmNP : NumberPicker,
        caloriesNP : NumberPicker,
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

        initializeBsaeActivityControls(
            durationHrNP,
            durationmNP,
            caloriesNP,
            durationHrMax,
            defaultValueDurationHr
        )
    }

    fun initializeBsaeActivityControls(
        durationHrNP : NumberPicker,
        durationmNP : NumberPicker,
        caloriesNP : NumberPicker,
        durationHrMax : Int,
        defaultValueDurationHr : Int)
    {
        durationHrNP.minValue = 0
        durationHrNP.maxValue = durationHrMax
        durationHrNP.value = defaultValueDurationHr

        durationmNP.minValue = 0
        durationmNP.maxValue = 59
        durationmNP.value = 0

        caloriesNP.minValue = 0
        caloriesNP.maxValue = 2500
        caloriesNP.value = 500
    }
}