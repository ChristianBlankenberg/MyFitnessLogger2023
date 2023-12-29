package com.example.myfitnesslogger.ui.viewmodels

import androidx.fragment.app.FragmentActivity

import com.example.myfitnesslogger.businesslogic.sharedPrefGate
import com.example.myfitnesslogger.businesslogic.infoType

class SettingsViewModel() : BaseViewModel() {
    fun SetUrl(activity: FragmentActivity?, url: String, type: infoType) {
        sharedPrefGate.SetUrl(activity, url, type)
    }
}