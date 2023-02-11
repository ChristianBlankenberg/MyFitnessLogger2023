package com.CBPrograms.myfitnesslogger2023.ui.baseClasses

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.utils.globalVariables
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

open class SendInfoBaseViewModel : ViewModel() {
    protected lateinit var activity : Activity

    fun initialize(activity : Activity)
    {
        this.activity = activity
    }

    fun canSendData(): Boolean {
        return globalVariables.URL != ""
    }

    fun getPastInformationFlow(informationType : informationType, continous : Boolean, deltaDays: Int, fragmentActivity : FragmentActivity? = null): Flow<ArrayList<String>> {
        return dataGate.getAFlow(informationType, continous, LocalDateTime.now().plusDays(deltaDays.toLong()), fragmentActivity)
    }
}