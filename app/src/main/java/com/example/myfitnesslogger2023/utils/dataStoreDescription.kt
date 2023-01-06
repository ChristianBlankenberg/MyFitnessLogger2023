package com.example.myfitnesslogger2023.utils

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.FieldValue
import java.time.LocalDateTime
import java.util.*

class dataStoreDescription(
    val dataStoreType: dataStoreType,
    val informationType: informationType,
    val value: Double?,
    val dateTime: LocalDateTime,
    val fragmentActivity: FragmentActivity? = null) {

    fun getUrl(): String
    {
        return globalVariables.URL
    }

    fun getSheet(): String
    {
        return getSheetOrDatabaseOf(this.informationType).toString()
    }

    fun getColumn() : Int {
        return when (informationType) {
            com.example.myfitnesslogger2023.utils.informationType.weight -> 2
            com.example.myfitnesslogger2023.utils.informationType.kfa -> 3
            else -> -1
        }
    }

    fun getRow() : Int {
        return  this.dateTime.dayOfYear + 1
    }

    private fun getSheetOrDatabaseOf(informationType: informationType) : googleSheetType
    {
        return when(informationType)
        {
            com.example.myfitnesslogger2023.utils.informationType.weight -> googleSheetType.dataSheet
            com.example.myfitnesslogger2023.utils.informationType.kfa -> googleSheetType.dataSheet
            else -> googleSheetType.unknown
        }
    }

    fun getValueString(): String {
        return when (dataStoreType) {
            com.example.myfitnesslogger2023.utils.dataStoreType.googleSheets -> getDoubleValueString(
                this.value
            )
            else -> this.value.toString()
        }
    }

    private fun getDoubleValueString(number: Double?): String {
        return number.toString().replace(".", ",")
    }

    fun getData(): HashMap<String, Any> {
        return when(informationType)
        {
            com.example.myfitnesslogger2023.utils.informationType.weight ->
                hashMapOf<String, Any>(
                    "date" to this.dateTime,
                    "type" to this.informationType,
                    "value" to (this.value ?: "no value"),
                    "timestamp" to FieldValue.serverTimestamp(),
                    "database" to  this.getDatabase()
                )
            else -> hashMapOf<String, Any>()
        }
    }

    fun getCollection(): String {
        return "FitnessData" + LocalDateTime.now().year
    }

    private fun getDatabase(): String {
        return getSheetOrDatabaseOf(this.informationType).toString()
    }

    fun getKey(): String {
        return this.informationType.toString()+this.getRow().toString()
    }

    fun getActivity(): FragmentActivity? {
        return fragmentActivity
    }
}