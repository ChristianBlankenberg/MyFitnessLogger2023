package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import androidx.fragment.app.FragmentActivity
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions
import java.time.LocalDateTime

class SleepDurationViewModel : SendInfoBaseViewModel() {

    fun sendSleepDuration(hours: Int, minutes: Int, fragementActivity: FragmentActivity? = null) {
        dataGate.sendSleepDuration( mathFunctions.getMinutes(hours, minutes), LocalDateTime.now(), fragementActivity)
    }
}