package com.example.myfitnesslogger2023.utils

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.example.myfitnesslogger2023.enumerations.activityType
import com.google.firebase.firestore.FieldValue
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class dataStoreDescription(
    val dataStoreType: dataStoreType,
    val informationType: informationType,
    val values: ArrayList<Any>,
    val dateTime: LocalDateTime,
    val activity: FragmentActivity? = null,
) {

    fun getDataActions(): ArrayList<dataStoreAction> {
        return getDataDataStoreActions()
    }

    private fun getActivityType(): activityType {
        if (values.first() is activityType) {
            return values.first() as activityType
        }

        return activityType.unknown
    }

    private fun getDataDataStoreActions(): ArrayList<dataStoreAction> {
        val columns = getColumns()
        val keys = getKeys()
        val result = arrayListOf<dataStoreAction>()
        val datas = getData()
        val valueStrings = getValueStrings()

        if (columns.count() == keys.count()) {
            for (idx in 0..columns.count() - 1)
                result.add(
                    dataStoreAction(
                        getUrl(),
                        getSheet(),
                        columns.get(idx),
                        getRow(),
                        valueStrings.get(idx),
                        keys.get(idx),
                        getCollection(),
                        datas.get(idx)
                    )
                )
        }

        return result
    }

    private fun getUrl(): String {
        return globalVariables.URL
    }

    private fun getSheet(): String {
        return getSheetOrDatabaseOf(this.informationType).toString()
    }

    private fun getSheetOrDatabaseOf(informationType: informationType): googleSheetType {
        return when (informationType) {
            com.example.myfitnesslogger2023.utils.informationType.weight,
            com.example.myfitnesslogger2023.utils.informationType.kfa,
            com.example.myfitnesslogger2023.utils.informationType.sleepduration,
            com.example.myfitnesslogger2023.utils.informationType.information -> googleSheetType.dataSheet
            com.example.myfitnesslogger2023.utils.informationType.activity -> googleSheetType.activitySheet
            else -> googleSheetType.unknown
        }
    }

    private fun getColumns(): ArrayList<Int> {
        return when (informationType) {
            com.example.myfitnesslogger2023.utils.informationType.weight -> arrayListOf<Int>(2)
            com.example.myfitnesslogger2023.utils.informationType.kfa -> arrayListOf<Int>(3)
            com.example.myfitnesslogger2023.utils.informationType.sleepduration -> arrayListOf<Int>(
                4
            )
            com.example.myfitnesslogger2023.utils.informationType.information -> arrayListOf<Int>(5)
            com.example.myfitnesslogger2023.utils.informationType.activity -> {
                return arrayListOf(3, 4, 9)
            }
            else -> arrayListOf<Int>()
        }
    }

    private fun getRow(): Int {
        return this.dateTime.dayOfYear + 1
    }

    private fun getValueStrings(): ArrayList<String> {
        return if (informationType == com.example.myfitnesslogger2023.utils.informationType.activity) {
            arrayListOf(
                getValueString(values.get(1)),
                getValueString(values.get(2)),
                getValueString(values.get(3))
            )
        } else {
            arrayListOf(getValueString(this.firstValue().toString()))
        }
    }

    fun getValueString(value: Any?): String {
        return when (dataStoreType) {
            com.example.myfitnesslogger2023.utils.dataStoreType.googleSheets -> {
                return if (informationType == com.example.myfitnesslogger2023.utils.informationType.information) {
                    value?.toString() ?: "?"
                } else {
                    getDoubleValueString(
                        value
                    )
                }
            }
            else -> value.toString()
        }
    }

    private fun getDoubleValueString(number: Any?): String {
        return number.toString().replace(".", ",")
    }

    private fun getKeys(): ArrayList<String> {
        if (this.informationType == com.example.myfitnesslogger2023.utils.informationType.activity) {
            //ToDo
            return arrayListOf(
                "activityDuration" + this.getRow().toString(),
                "activityCalories" + this.getRow().toString(),
                getActivityType().toString() + this.getRow().toString()
            )
        } else {
            return arrayListOf(this.informationType.toString() + this.getRow().toString())
        }
    }

    private fun getCollection(): String {
        return "FitnessData" + LocalDateTime.now().year
    }

    fun getData(): ArrayList<HashMap<String, Any>> {
        return when (informationType) {
            com.example.myfitnesslogger2023.utils.informationType.weight,
            com.example.myfitnesslogger2023.utils.informationType.kfa,
            com.example.myfitnesslogger2023.utils.informationType.sleepduration,
            com.example.myfitnesslogger2023.utils.informationType.information ->
                arrayListOf(
                    hashMapOf<String, Any>(
                        "date" to this.dateTime,
                        "type" to this.informationType,
                        "value" to (this.firstValue()?.toString() ?: "no value"),
                        "timestamp" to FieldValue.serverTimestamp(),
                        "database" to this.getDatabase()
                    )
                )
            com.example.myfitnesslogger2023.utils.informationType.activity -> {
                val result = arrayListOf<HashMap<String, Any>>()

                values.forEach {
                    result.add(
                        hashMapOf<String, Any>(
                            "date" to this.dateTime,
                            "type" to this.informationType,
                            "value" to (it?.toString() ?: "no value"),
                            "timestamp" to FieldValue.serverTimestamp(),
                            "database" to this.getDatabase()
                        )
                    )
                }

                return result;
            }
            else -> arrayListOf(hashMapOf<String, Any>())
        }
    }

    private fun getDatabase(): String {
        return getSheetOrDatabaseOf(this.informationType).toString()
    }

    private fun firstValue(): Any? {
        return values.firstOrNull()
    }
}