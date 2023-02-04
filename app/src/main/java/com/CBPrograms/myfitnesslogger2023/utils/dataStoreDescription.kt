package com.CBPrograms.myfitnesslogger2023.utils

import androidx.fragment.app.FragmentActivity
import com.CBPrograms.myfitnesslogger2023.enumerations.dataStoreType
import com.CBPrograms.myfitnesslogger2023.enumerations.googleSheetType
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.google.firebase.firestore.FieldValue
import java.time.LocalDateTime
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

    private fun getActivityType(): com.CBPrograms.myfitnesslogger2023.enumerations.activityType {
        if (values.count() > 0 && values.first() is com.CBPrograms.myfitnesslogger2023.enumerations.activityType) {
            return values.first() as com.CBPrograms.myfitnesslogger2023.enumerations.activityType
        }

        return com.CBPrograms.myfitnesslogger2023.enumerations.activityType.unknown
    }

    private fun getDataDataStoreActions(): ArrayList<dataStoreAction> {
        val columns = getColumns()
        val keys = getKeys()
        val result = arrayListOf<dataStoreAction>()
        val datas = getData()
        val valueStrings = getValueStrings()

        var defaultHashmap = hashMapOf<String, Any>()

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
                        if (idx < datas.count()) datas.get(idx) else defaultHashmap
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
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.weight,
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.kfa,
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.sleepduration,
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.information -> googleSheetType.dataSheet
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.activity -> googleSheetType.activitySheet
            else -> googleSheetType.unknown
        }
    }

    private fun getColumns(): ArrayList<Int> {
        return when (informationType) {
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.weight -> arrayListOf<Int>(2)
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.kfa -> arrayListOf<Int>(3)
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.sleepduration -> arrayListOf<Int>(
                4
            )
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.information -> arrayListOf<Int>(5)
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.activity -> {
                return when(getActivityType())
                {
                    com.CBPrograms.myfitnesslogger2023.enumerations.activityType.jogging -> arrayListOf(3, 4, 9)
                    com.CBPrograms.myfitnesslogger2023.enumerations.activityType.cycling -> arrayListOf(3, 4, 10)
                    com.CBPrograms.myfitnesslogger2023.enumerations.activityType.hiking -> arrayListOf(3, 4, 11)
                    com.CBPrograms.myfitnesslogger2023.enumerations.activityType.workout ->  arrayListOf(3, 4, 12)
                    else -> arrayListOf()
                }
            }
            else -> arrayListOf<Int>()
        }
    }

    private fun getRow(): Int {
        return this.dateTime.dayOfYear + 1
    }

    private fun getValueStrings(): ArrayList<String> {
        return if (informationType == com.CBPrograms.myfitnesslogger2023.enumerations.informationType.activity) {
            if (getActivityType() == com.CBPrograms.myfitnesslogger2023.enumerations.activityType.workout)
            {
                arrayListOf(
                    getValueString(values, 1),
                    getValueString(values, 2),
                    getValueString(values,2))

            }
            else {
                arrayListOf(
                    getValueString(values, 1),
                    getValueString(values,2),
                    getValueString(values, 3))
            }
        } else {
            arrayListOf(getValueString(this.firstValue().toString()))
        }
    }

    private fun getValueString(list : ArrayList<Any>, idx : Int) : String
    {
        if (list.count() > idx)
        {
            return getValueString(list.get(idx))
        }
        else
        {
            return ""
        }
    }

    fun getValueString(value: Any?): String {
        return when (dataStoreType) {
            com.CBPrograms.myfitnesslogger2023.enumerations.dataStoreType.googleSheets -> {
                return if (informationType == com.CBPrograms.myfitnesslogger2023.enumerations.informationType.information) {
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
        if (this.informationType == com.CBPrograms.myfitnesslogger2023.enumerations.informationType.activity) {
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
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.weight,
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.kfa,
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.sleepduration,
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.information ->
                arrayListOf(
                    hashMapOf<String, Any>(
                        "date" to this.dateTime,
                        "type" to this.informationType,
                        "value" to (this.firstValue()?.toString() ?: "no value"),
                        "timestamp" to FieldValue.serverTimestamp(),
                        "database" to this.getDatabase()
                    )
                )
            com.CBPrograms.myfitnesslogger2023.enumerations.informationType.activity -> {
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
