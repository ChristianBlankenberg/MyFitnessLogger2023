package com.example.myfitnesslogger2023.ui.activity

import android.content.Context
import com.example.myfitnesslogger2023.ui.baseClasses.ActivitySendInfoBaseFragment

abstract class TabulatorChildFragment(context: Context?) : ActivitySendInfoBaseFragment(), ITabulatorChildFragment {

    override val Title : String
        get() = GetTitle()

    protected abstract fun GetTitle() : String;
}