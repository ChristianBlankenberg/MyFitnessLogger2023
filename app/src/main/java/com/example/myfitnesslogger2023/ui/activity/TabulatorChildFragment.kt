package com.example.myfitnesslogger2023.ui.activity

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.myfitnesslogger2023.ui.baseClasses.SendInfoBaseFragment

abstract class TabulatorChildFragment(context: Context?) : SendInfoBaseFragment(), ITabulatorChildFragment {

    override val Title : String
        get() = GetTitle()

    protected abstract fun GetTitle() : String;
}