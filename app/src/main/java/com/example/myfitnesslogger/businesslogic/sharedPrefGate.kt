package com.example.myfitnesslogger.businesslogic

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

object sharedPrefGate {

    fun GetUrl(activity : FragmentActivity?, type: infoType) : String
    {
        val url = getSharedPreferences(activity)?.getString("url".plus(type.toString()), "-")
        return url ?: ""
    }

    fun SetUrl(activity : FragmentActivity?, url : String, type: infoType)
    {
        getSharedPreferences(activity)?.edit()?.putString("url".plus(type.toString()), url)?.apply()
    }

    private fun getSharedPreferences(activity : FragmentActivity?) : SharedPreferences?
    {
        return activity?.getPreferences(Context.MODE_PRIVATE)
    }

    fun setValue(activity: FragmentActivity?, key: String, value: String) {
        getSharedPreferences(activity)?.edit()?.putString(key, value)?.apply()
    }

    fun getValue(activity: FragmentActivity?, key: String) : String {
        val value = getSharedPreferences(activity)?.getString(key, "")
        return value ?: ""
    }
}