package com.example.myfitnesslogger2023.ui.activity
import androidx.fragment.app.FragmentActivity
import com.example.myfitnesslogger.businesslogic.dataGate
import com.example.myfitnesslogger2023.enumerations.activityType
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import java.time.LocalDateTime

class JoggingViewModel : SendInfoBaseViewModel() {

    fun sendJogginActivity(
        distanceKm: Int,
        distancem: Int,
        durationHr: Int,
        durationMin: Int,
        calories: Int,
        fragementActivity: FragmentActivity? = null) {
        dataGate.sendActivity(
            activityType.jogging,
            distanceKm.toDouble() + distancem.div(100.toDouble()),
            durationHr.times(60) + durationMin,
            calories,
            LocalDateTime.now(),
            fragementActivity)
    }
}