package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import androidx.fragment.app.FragmentActivity
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions
import java.time.LocalDateTime

class WeightKFAViewModel : SendInfoBaseViewModel() {

    //region Send functions
    fun sendData(
        weightGreat : Int,
        weightSmall : Int,
        kfaGreat : Int,
        kfaSmall : Int,
        hasKFAValue : Boolean,
        fragmentActivity : FragmentActivity? = null)
    {
        dataGate.sendWeight(weightGreat + weightSmall / 10.0, LocalDateTime.now(), fragmentActivity)
        if (hasKFAValue)
        {
            dataGate.sendKFA( mathFunctions.getDoubleValue(kfaGreat,  kfaSmall), LocalDateTime.now(), fragmentActivity)
        }
    }
}