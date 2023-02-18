package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import androidx.fragment.app.FragmentActivity
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import java.time.LocalDateTime

class InfoViewModel : SendInfoBaseViewModel() {

    fun sendInformation(information : String, fragementActivity: FragmentActivity? = null) {
        dataGate.sendInformation(information, LocalDateTime.now(), fragementActivity)
    }

}