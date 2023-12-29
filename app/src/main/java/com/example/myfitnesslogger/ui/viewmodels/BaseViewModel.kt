package com.example.myfitnesslogger.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.myfitnesslogger.businesslogic.dataSetType
import com.example.myfitnesslogger.businesslogic.sharedPrefGate
import com.example.myfitnesslogger.businesslogic.infoType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalDate as LocalDate

open class BaseViewModel() : ViewModel() {
    protected lateinit var activity: FragmentActivity

    fun initialize(activity: FragmentActivity)
    {
        this.activity = activity
    }

    fun getDay(deltaDays : Long) : Int
    {
        return LocalDate.now().plusDays(deltaDays).dayOfMonth; // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }

    fun getMonth(deltaDays : Long) : Int
    {
        return LocalDate.now().plusDays(deltaDays).monthValue; // Calendar.getInstance().get(Calendar.MONTH) + 1
    }

    fun getYear(deltaDays : Long) : Int
    {
        return LocalDate.now().plusDays(deltaDays).year; // Calendar.getInstance().get(Calendar.MONTH) + 1
    }

    fun getTimeddmMyyyy() : String
    {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return current.format(formatter)
    }

    protected fun getSuffix(deltaDays : Long) : String
    {
        return this.getDay(deltaDays).toString().plus(";").plus(getMonth(deltaDays).toString()).plus(";").plus(getYear(deltaDays).toString())
    }

    protected fun getIndexedString(s : String, index : Int) : String
    {
        val parts = s.split(";")
        if (parts.count() > index)
        {
            return parts[index]
        }

        return ""
    }

    protected fun getCachedDoubleValue(dataSet: dataSetType, key : String) : String {
        return this.getCachedValue(dataSet, key).replace(",", ".")
    }

    protected fun getCachedValue(dataSet: dataSetType, key : String) : String {
        return sharedPrefGate.getValue(this.activity, key)
    }

    fun getPastWeight(deltaDays : Long): String {
        return this.getCachedDoubleValue(dataSetType.WeightKFA, "weight".plus(";").plus(this.getSuffix(deltaDays = deltaDays)))
    }

    fun getPastKFA(deltaDays: Long): String {
        return this.getCachedDoubleValue(dataSetType.WeightKFA, "kfa".plus(";").plus(this.getSuffix(deltaDays = deltaDays)))
    }

    private fun GetUrl(activity: FragmentActivity?, type : infoType): String {
        return sharedPrefGate.GetUrl(activity, type)
    }

    fun GetUrl(type : infoType): String {
        return this.GetUrl(this.activity, type)
    }
}