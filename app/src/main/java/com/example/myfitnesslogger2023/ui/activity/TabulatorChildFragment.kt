package com.example.myfitnesslogger2023.ui.activity

import android.content.Context
import com.example.myfitnesslogger2023.ui.baseClasses.ActivitySendInfoBaseFragment

abstract class TabulatorChildFragment : ActivitySendInfoBaseFragment(), ITabulatorChildFragment {
    protected var contextRef: Context? = null

    override val Title : String
        get() = GetTitle()

    fun setParentContext(parentContext : Context?)
    {
        this.contextRef = parentContext
    }
    protected abstract fun GetTitle() : String;

}