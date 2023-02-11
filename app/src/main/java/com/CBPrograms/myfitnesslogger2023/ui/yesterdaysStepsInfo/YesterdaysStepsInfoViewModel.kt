package com.CBPrograms.myfitnesslogger2023.ui.yesterdaysStepsInfo

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import java.time.LocalDateTime

class YesterdaysStepsInfoViewModel : SendInfoBaseViewModel() {
    fun sendYesterdaySteps(yesterdaySteps: Int, activity: FragmentActivity?) {
        dataGate.sendSteps(yesterdaySteps, LocalDateTime.now().minusDays(1), activity)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}