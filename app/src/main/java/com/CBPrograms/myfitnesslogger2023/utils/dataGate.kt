package com.CBPrograms.myfitnesslogger2023.businesslogic

import androidx.fragment.app.FragmentActivity
import com.CBPrograms.myfitnesslogger2023.businesslogic.sharedPrefGate.getValue
import com.CBPrograms.myfitnesslogger2023.enumerations.activityType
import com.CBPrograms.myfitnesslogger2023.utils.dataStoreDescription
import com.CBPrograms.myfitnesslogger2023.utils.dataStoreType
import com.CBPrograms.myfitnesslogger2023.epositories.firebaseFireStoreService
import com.CBPrograms.myfitnesslogger2023.utils.informationType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

object dataGate {
    val httpService: httpRequestService = httpRequestService()

    fun sendWeight(
        weight: Double,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity? = null
    ) {
        val dataStoreDescriptions = getDataStoreDescriptions(
            informationType.weight,
            arrayListOf(weight),
            dateTime,
            fragementActivity
        )
        for (dataStoreDescription in dataStoreDescriptions) {
            sendValue(dataStoreDescription)
        }
    }

    fun sendKFA(kfa: Double, dateTime: LocalDateTime, fragementActivity: FragmentActivity? = null) {
        val dataStoreDescriptions = getDataStoreDescriptions(
            informationType.kfa,
            arrayListOf(kfa),
            dateTime,
            fragementActivity
        )
        for (dataStoreDescription in dataStoreDescriptions) {
            sendValue(dataStoreDescription)
        }
    }

    fun sendSleepDuration(
        minutes: Int,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity? = null
    ) {
        val dataStoreDescriptions = getDataStoreDescriptions(
            informationType.sleepduration,
            arrayListOf(minutes.toDouble()),
            dateTime,
            fragementActivity
        )
        for (dataStoreDescription in dataStoreDescriptions) {
            sendValue(dataStoreDescription)
        }
    }

    fun sendInformation(
        information: String,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity?
    ) {
        val dataStoreDescriptions = getDataStoreDescriptions(
            informationType.information,
            arrayListOf(information),
            dateTime,
            fragementActivity
        )
        for (dataStoreDescription in dataStoreDescriptions) {
            sendValue(dataStoreDescription)
        }
    }

    fun sendActivity(activity: com.CBPrograms.myfitnesslogger2023.enumerations.activityType,
                     distance: Double,
                     minutes: Int,
                     calories: Int,
                     dateTime: LocalDateTime,
                     fragementActivity: FragmentActivity?) {
        val dataStoreDescriptions = getDataStoreDescriptions(
            informationType.activity,
            arrayListOf(
                activity,
                minutes,
                calories,
                distance),
            dateTime,
            fragementActivity
        )

        for (dataStoreDescription in dataStoreDescriptions) {
            sendValue(dataStoreDescription)
        }
    }

    fun getActivity(activity: com.CBPrograms.myfitnesslogger2023.enumerations.activityType,
                    dateTime: LocalDateTime,
                    fragementActivity: FragmentActivity?) : ArrayList<String> {
        return getAValue(dateTime, informationType.activity, arrayListOf(activity),fragementActivity)
    }

    fun getKFA(dateTime: LocalDateTime, fragementActivity: FragmentActivity? = null): Double? {
        return getAValue(dateTime, informationType.kfa, arrayListOf(), fragementActivity).firstOrNull()?.toDoubleOrNull()
    }

    fun getWeight(dateTime: LocalDateTime, fragementActivity: FragmentActivity? = null): Double? {
        return getAValue(dateTime, informationType.weight, arrayListOf(), fragementActivity).firstOrNull()?.toDoubleOrNull()
    }

    fun getSleepDurationMinutes(
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity?
    ): Double? {
        return getAValue(
            dateTime,
            informationType.sleepduration,
            arrayListOf(),
            fragementActivity
        ).firstOrNull()?.toDoubleOrNull()
    }

    fun getInformation(dateTime: LocalDateTime, fragementActivity: FragmentActivity?): String {
        return getAValue(dateTime, informationType.information, arrayListOf(), fragementActivity).firstOrNull() ?: ""
    }

    private fun getAValue(
        dateTime: LocalDateTime,
        informationType: informationType,
        values: ArrayList<Any>,
        fragementActivity: FragmentActivity? = null
    ): ArrayList<String> {
        val dataStoreDescription = dataStoreDescription(
            dataStoreType.device,
            informationType,
            values,
            dateTime,
            fragementActivity
        )

        var result = getValue(dataStoreDescription)

        if (result.count() == 0 || result.all { it == ""}) {
            val dsc = dataStoreDescription(
                dataStoreType.googleSheets,
                informationType,
                values,
                dateTime,
                fragementActivity
            )
            result = getValue(dsc)
        }

        return result
    }

    private fun getDataStoreDescriptions(
        informationType: informationType,
        values: ArrayList<Any>,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity? = null
    ): ArrayList<dataStoreDescription> {
        return arrayListOf<dataStoreDescription>(
            dataStoreDescription(
                dataStoreType.googleSheets,
                informationType,
                values,
                dateTime,
                fragementActivity
            ),
            dataStoreDescription(
                dataStoreType.fireStore,
                informationType,
                values,
                dateTime,
                fragementActivity
            ),
            dataStoreDescription(
                dataStoreType.device,
                informationType,
                values,
                dateTime,
                fragementActivity
            )
        )
    }

    private fun getValue(dataStoreDescription: dataStoreDescription): ArrayList<String> {
        return when (dataStoreDescription.dataStoreType) {
            dataStoreType.googleSheets -> {
                val result = arrayListOf<String>()
                dataStoreDescription.getDataActions().forEach()
                {
                    result.add(
                        httpService.sendGet(
                            it.url,
                            this@dataGate.getParameter(
                                this@dataGate.getValueParameter(),
                                it.sheetName,
                                it.col,
                                it.row,
                                it.value
                            )
                        )
                    )
                }

                return result
            }

            dataStoreType.fireStore -> {
                arrayListOf<String>()
            }

            dataStoreType.device -> {
                val result = arrayListOf<String>()
                dataStoreDescription.getDataActions().forEach()
                {
                    result.add(getValue(dataStoreDescription.activity, it.key))
                }

                return result
            }

            else -> {
                arrayListOf<String>()
            }
        }
    }

    private fun sendValue(dataStoreDescription: dataStoreDescription) {
        when (dataStoreDescription.dataStoreType) {
            dataStoreType.googleSheets -> {
                GlobalScope.launch {
                    dataStoreDescription.getDataActions().forEach()
                    {

                        if (httpService.sendGet(
                                it.url,
                                this@dataGate.getParameter(
                                    this@dataGate.setValueParameter(),
                                    it.sheetName,
                                    it.col,
                                    it.row,
                                    it.value
                                )
                            ) != "OK"
                        ) {
                            //ToDo : add error handler
                        }
                    }
                }
            }

            dataStoreType.fireStore -> {
                dataStoreDescription.getDataActions().forEach()
                {
                    com.CBPrograms.myfitnesslogger2023.epositories.firebaseFireStoreService.addData(
                        it.collection,
                        it.data)
                }
/*                firebaseFireStoreService.addData(
                    dataStoreDescription.getCollection(),
                    dataStoreDescription.getData()
                )*/
            }

            dataStoreType.device -> {
                dataStoreDescription.getDataActions().forEach()
                {
                    sharedPrefGate.setValue(
                        dataStoreDescription.activity,
                        it.key,
                        it.value)
                }
                /*sharedPrefGate.setValue(
                    dataStoreDescription.getActivity(),
                    dataStoreDescription.getKey(),
                    dataStoreDescription.getValueString()
                )*/
            }

            else -> {
            }
        }
    }

/*    private fun getValue(url: String, sheetName: String, col: Int, row: Int): String {
        return httpService.sendGet(
            url,
            this.getParameter(this.getValueParameter(), sheetName, col, row, "")
        )
    }*/

    private fun getIntValueString(number: Int): String {
        return number.toString()
    }

    private fun getValueParameter(): String {
        return "Get"
    }

    private fun setValueParameter(): String {
        return "Set"
    }

    private fun getParameter(
        order: String,
        sheetName: String,
        col: Int,
        row: Int,
        value: String,
    ): String {
        var result = "Ex=" + order + "&Sh=" + sheetName + "&Col=" + col + "&Row=" + row
        if (order == setValueParameter())
            result = result.plus("&Val=" + value)

        result = result.plus("&Id=").plus(getID())

        return result
    }

    fun getID(): String {
        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.format(Date())

        var result = ""
        var lastNumber = 5
        for (i in date.length - 1 downTo 0) {
            var number = (date.substring(i, i + 1)).toIntOrNull()

            if (number == null) {
                number = 0
            }

            result = result + (number + lastNumber)
            lastNumber = number
        }

        return result
    }

    /*
    fun sendWeight(url: String, weight: Double): Boolean {
        val col = 1
        val row = getDayRow()
        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                this.getDataSheet(),
                col,
                row,
                getDoubleValueString(weight)
            )
        ) == "OK"
    }

    fun getWeight(url: String): Double {
        val col = 1
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row).toDoubleOrNull() ?: 0.0
    }

    fun sendKFA(url: String, kfa: Double): Boolean {
        val col = 2
        val row = getDayRow()
        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                this.getDataSheet(),
                col,
                row,
                getDoubleValueString(kfa)
            )
        ) == "OK"
    }

    fun getKFA(url: String): Double {
        val col = 2
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row).toDoubleOrNull() ?: 0.0
    }

    fun sendCombinedWeightAndKFA(url: String, weight: String, kfa: String): Boolean {
        val col = 9
        val row = getDayRow()

        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                this.getDataSheet(),
                col,
                row,
                weight.plus(";").plus(kfa).plus(";")
            )
        ) == "OK"
    }

    fun getTrainingMinutes(url: String): Int {
        val col = 3
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row).toIntOrNull() ?: 0
    }

    fun sendTrainingMinutes(url: String, trainingMinutes: Int): Boolean {
        val col = 3
        val row = getDayRow()
        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                this.getDataSheet(),
                col,
                row,
                trainingMinutes.toString()
            )
        ) == "OK"
    }

    fun getCalories(url: String): Int {
        val col = 4
        val row = getDayRow()
        val value = this.getValue(url, this.getDataSheet(), col, row)
        return value.toIntOrNull() ?: 0
    }

    fun sendCalories(url: String, calories: Int): Boolean {
        val col = 4
        val row = getDayRow()
        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                this.getDataSheet(),
                col,
                row,
                calories.toString()
            )
        ) == "OK"
    }

    fun sendCombinedTrainingMinutesAndCalories(
        url: String,
        trainingMinutes: Int,
        calories: Int
    ): Boolean {
        val col = 10
        val row = getDayRow()
        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                this.getDataSheet(),
                col,
                row,
                getIntValueString(trainingMinutes).plus(";").plus(getIntValueString(calories))
                    .plus(";")
            )
        ) == "OK"
    }

    fun getCombinedTrainingMinutesAndCalories(url: String): String {
        val col = 10
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row)
    }

    fun getCombinedTodaysData(url: String): String {
        val col = 11
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row)
    }

    fun getCombinedMonthData(url: String): String {
        val col = 6
        val row = getMonthRow()
        return this.getValue(url, this.getDataEvaluationSheet(), col, row)
    }

    fun getCombinedPrevMonthData(url: String): String {
        val col = 6
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        if (month > 1) {
            val row = getMonthRow(month - 1)
            return this.getValue(url, this.getDataEvaluationSheet(), col, row)
        } else {
            return ""
        }
    }

    fun sendRemarks(url: String, remarks: String): Boolean {
        val col = 6
        val row = getDayRow()
        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                this.getDataSheet(),
                col,
                row,
                URLEncoder.encode(remarks, "UTF-8")
            )
        ) == "OK"

        return true
    }

    fun getRemarks(url: String): String {
        val col = 6
        val row = getDayRow()
        return URLDecoder.decode(this.getValue(url, this.getDataSheet(), col, row), "UTF-8")
    }

    fun getTime(url: String): String {
        val col = 5
        val row = getDayRow()
        return this.getValue(url, this.getDataSheet(), col, row).replace("-", ":").replace("!", "")
    }

    fun sendTime(url: String): Boolean {
        val col = 5
        val row = getDayRow()

        val timeZone = TimeZone.getTimeZone("Europe/Berlin")
        val calendar = Calendar.getInstance(timeZone)
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
        simpleDateFormat.timeZone = timeZone;

        var time = "!".plus(simpleDateFormat.format(Calendar.getInstance().time).replace(":", "-"))

        return httpService.sendGet(
            url,
            this.getParameter(
                this.setValueParameter(),
                this.getDataSheet(),
                col,
                row,
                time
            )
        ) == "OK"
    }

    fun getMonthAverageWeight(url: String): String {
        return this.getValue(
            url,
            this.getDataEvaluationSheet(),
            2,
            Calendar.getInstance().get(Calendar.MONTH) + 1 + 1
        )
    }

    fun getMonthAverageKFA(url: String): String {
        return this.getValue(
            url,
            this.getDataEvaluationSheet(),
            3,
            Calendar.getInstance().get(Calendar.MONTH) + 1 + 1
        )
    }

    fun getMonthTrainingHours(url: String): String {
        return this.getValue(
            url,
            this.getDataEvaluationSheet(),
            4,
            Calendar.getInstance().get(Calendar.MONTH) + 1 + 1
        )
    }

    fun getMonthMegaCalories(url: String): String {
        return this.getValue(
            url,
            this.getDataEvaluationSheet(),
            5,
            Calendar.getInstance().get(Calendar.MONTH) + 1 + 1
        )
    }

    private fun getDayRow(): Int {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        return (month - 1) * 33 + 1 + day
    }

    private fun getMonthRow() : Int
    {
        return this.getMonthRow(Calendar.getInstance().get(Calendar.MONTH) + 1)
    }

    private fun getMonthRow(month : Int): Int {
        return month + 1
    }

    private fun getValueParameter(): String {
        return "Get"
    }

    private fun setValueParameter(): String {
        return "Set"
    }

    private fun getDataSheet(): String {
        return "data"
    }

    private fun getDataEvaluationSheet(): String {
        return "dataevaluation"
    }

    private fun getParameter(
        order: String,
        sheetName: String,
        col: Int,
        row: Int,
        value: String,
    ): String {
        var result = "Ex=" + order + "&Sh=" + sheetName + "&Col=" + col + "&Row=" + row
        if (order == setValueParameter())
            result = result.plus("&Val=" + value)

        return result
    }

     */
}