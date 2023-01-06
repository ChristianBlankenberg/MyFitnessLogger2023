package com.example.myfitnesslogger2023.ui.sleepAndInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfitnesslogger2023.ui.baseClasses.BaseViewModel

class SleepAndInfoViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}