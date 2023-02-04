package com.CBPrograms.myfitnesslogger2023.ui.sleepAndInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel

class SleepAndInfoViewModel : SendInfoBaseViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}