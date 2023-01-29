package com.CBPrograms.myfitnesslogger2023.businesslogic

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

object sharedPrefGate {

    fun setValue(activity: FragmentActivity?, key: String, value: String) {
        getSharedPreferences(activity)?.edit()?.putString(key, value)?.apply()
    }

    fun getValue(activity: FragmentActivity?, key: String) : String {
        val value = getSharedPreferences(activity)?.getString(key, "")
        return value ?: ""
    }

    private fun getSharedPreferences(activity : FragmentActivity?) : SharedPreferences?
    {
        return activity?.getPreferences(Context.MODE_PRIVATE)
    }
}