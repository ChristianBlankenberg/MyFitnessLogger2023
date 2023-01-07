package com.example.myfitnesslogger2023.ui.sleepAndInfo

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfitnesslogger.businesslogic.dataGate
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import com.example.myfitnesslogger2023.utils.informationType
import java.time.LocalDateTime
import java.util.*

class SleepAndInfoViewModel : SendInfoBaseViewModel() {

    fun sendInformation(information : String, fragementActivity: FragmentActivity? = null) {
        dataGate.sendInformation(information, LocalDateTime.now(), fragementActivity)
    }

    fun sendSleepDuration(hours: Int, minutes: Int, fragementActivity: FragmentActivity? = null) {
        dataGate.sendSleepDuration(hours * 60 + minutes, LocalDateTime.now(), fragementActivity)
    }

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
            return (sleepDurationMinutes.toInt() - hours * 60).toString()
        }
        else
        {
            return  "?"
        }
    }

    fun getInformation(fragementActivity: FragmentActivity? = null): String {
        return dataGate.getInformation(LocalDateTime.now(), fragementActivity) ?: ""
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}