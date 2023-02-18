package com.CBPrograms.myfitnesslogger2023.ui.activity

import androidx.fragment.app.FragmentActivity
import com.CBPrograms.myfitnesslogger2023.enumerations.activityType
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import java.time.LocalDateTime

open class SendActivityBaseViewModel(var activityType: com.CBPrograms.myfitnesslogger2023.enumerations.activityType) : SendInfoBaseViewModel() {

    fun sendActivityDistance(
        distance: Double,
        fragementActivity: FragmentActivity?) {
        dataGate.sendActivityDistance(activityType, distance, LocalDateTime.now(), fragementActivity)
    }

    fun sendActivityDuration(
        minutes: Int,
        fragementActivity: FragmentActivity?) {
        dataGate.sendActivityDuration(minutes, LocalDateTime.now(), fragementActivity)
    }

    fun sendActivityCalories(
        calories: Int,
        fragementActivity: FragmentActivity?) {
        dataGate.sendActivityCalories(calories, LocalDateTime.now(), fragementActivity)
    }

    fun sendActivityWorkoutCalories(
        calories: Int,
        fragementActivity: FragmentActivity?) {
        dataGate.sendActivityWorkoutCalories(calories, LocalDateTime.now(), fragementActivity)
    }

    /*
    fun sendActivity(
        distanceKm: Int,
        distancem: Int,
        durationHr: Int,
        durationMin: Int,
        calories: Int,
        fragementActivity: FragmentActivity? = null) {
        dataGate.sendActivity(
            this.activityType,
            distanceKm.toDouble() + distancem.div(100.toDouble()),
            durationHr.times(60) + durationMin,
            calories,
            LocalDateTime.now(),
            fragementActivity)
    }
*/

    fun getActivity(fragementActivity: FragmentActivity? = null) : ArrayList<String>
    {
        return dataGate.getActivity(
            this.activityType,
            LocalDateTime.now(),
            fragementActivity);
    }
}
