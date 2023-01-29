package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.CBPrograms.myfitnesslogger2023.businesslogic.dataGate
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import java.time.LocalDateTime

class WeightKFASleepInfoViewModel : SendInfoBaseViewModel() {

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
            dataGate.sendKFA(kfaGreat + kfaSmall / 10.0, LocalDateTime.now(), fragmentActivity)
        }
    }

    fun sendInformation(information : String, fragementActivity: FragmentActivity? = null) {
        dataGate.sendInformation(information, LocalDateTime.now(), fragementActivity)
    }

    fun sendSleepDuration(hours: Int, minutes: Int, fragementActivity: FragmentActivity? = null) {
        dataGate.sendSleepDuration(hours * 60 + minutes, LocalDateTime.now(), fragementActivity)
    }

    //endregion

    //region Get functions
    fun getPastKFA(deltaDays: Int, fragmentActivity : FragmentActivity? = null): Double?
    {
        return dataGate.getKFA(LocalDateTime.now().plusDays(deltaDays.toLong()), fragmentActivity)
    }

    fun getPastWeight(deltaDays: Int, fragmentActivity : FragmentActivity? = null): Double? {
        return dataGate.getWeight(LocalDateTime.now().plusDays(deltaDays.toLong()), fragmentActivity)
    }

    fun getTodaysSleepDurationMinutes(fragementActivity: FragmentActivity? = null): String {
        val sleepDurationMinutes = dataGate.getSleepDurationMinutes(LocalDateTime.now(), fragementActivity)
        if (sleepDurationMinutes != null)
        {
            val hours = (sleepDurationMinutes.toInt().div(60))
            return "%02d".format (sleepDurationMinutes.toInt() - hours * 60)
        }
        else
        {
            return  "?"
        }
    }
/*
        fun getTodaysSleepDurationHours(fragementActivity: FragmentActivity? = null): String {
        val sleepDurationMinutes = dataGate.getSleepDurationMinutes(LocalDateTime.now(), fragementActivity)
        if (sleepDurationMinutes != null)
        {
            return (sleepDurationMinutes.toInt().div(60)).toString()
        }
        else
        {
            return  "?"
        }
    }

    fun getTodaysSleepDurationMinutes(fragementActivity: FragmentActivity? = null): String {
        val sleepDurationMinutes = dataGate.getSleepDurationMinutes(LocalDateTime.now(), fragementActivity)
        if (sleepDurationMinutes != null)
        {
            val hours = (sleepDurationMinutes.toInt().div(60))
            return "%02d".format (sleepDurationMinutes.toInt() - hours * 60)
        }
        else
        {
            return  "?"
        }
    }
 */

    fun getInformation(fragementActivity: FragmentActivity? = null): String {
        return dataGate.getInformation(LocalDateTime.now(), fragementActivity) ?: ""
    }
    //endregion

}