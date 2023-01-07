package com.example.myfitnesslogger2023.ui.baseClasses

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.myfitnesslogger2023.utils.globalVariables

open class SendInfoBaseViewModel : ViewModel() {
    protected lateinit var activity : Activity

    fun initialize(activity : Activity)
    {
        this.activity = activity
    }

    fun canSendData(): Boolean {
        return globalVariables.URL != ""
    }
}