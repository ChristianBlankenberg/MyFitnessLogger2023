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


    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}