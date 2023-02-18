import com.CBPrograms.myfitnesslogger2023.businesslogic.httpRequestService

import androidx.fragment.app.FragmentActivity
import com.CBPrograms.myfitnesslogger2023.businesslogic.sharedPrefGate
import com.CBPrograms.myfitnesslogger2023.businesslogic.sharedPrefGate.getValue
import com.CBPrograms.myfitnesslogger2023.enumerations.activityType
import com.CBPrograms.myfitnesslogger2023.enumerations.comparisonType
import com.CBPrograms.myfitnesslogger2023.utils.dataStoreDescription
import com.CBPrograms.myfitnesslogger2023.enumerations.dataStoreType
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@DelicateCoroutinesApi
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

    fun sendSteps(steps: Int,
                  dateTime: LocalDateTime,
                  fragementActivity: FragmentActivity? = null) {
        val dataStoreDescriptions = getDataStoreDescriptions(
            informationType.steps,
            arrayListOf(steps),
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

    fun sendActivityDistance(
        activity: activityType,
        distance: Double,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity?) {

        var distanceInformationType = when(activity)
        {
            activityType.jogging -> informationType.activityDistanceJogging
            activityType.cycling -> informationType.activityDistanceCycling
            activityType.hiking -> informationType.activityDistanceHiking
            activityType.workout -> null
            else -> null
        }

        if (distanceInformationType != null)
        {
            val dataStoreDescriptions = getDataStoreDescriptions(
                distanceInformationType,
                arrayListOf(distance),
                dateTime,
                fragementActivity
            )

            for (dataStoreDescription in dataStoreDescriptions) {
                sendValue(dataStoreDescription)
            }
        }
    }

    fun sendActivityDuration(
        minutes: Int,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity?
    ) {
        var dataStoreDescriptions = getDataStoreDescriptions(
            informationType.activityTime,
            arrayListOf(minutes),
            dateTime,
            fragementActivity
        )

        for (dataStoreDescription in dataStoreDescriptions) {
            sendValue(dataStoreDescription)
        }
    }

    fun sendActivityCalories(
        calories: Int,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity?
    ) {
       val dataStoreDescriptions = getDataStoreDescriptions(
            informationType.activityCalories,
            arrayListOf(calories),
            dateTime,
            fragementActivity
        )

        for (dataStoreDescription in dataStoreDescriptions) {
            sendValue(dataStoreDescription)
        }
    }

    fun sendActivityWorkoutCalories(
        calories: Int,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity?
    ) {
        val dataStoreDescriptions = getDataStoreDescriptions(
            informationType.activityCaloriesWorkout,
            arrayListOf(calories),
            dateTime,
            fragementActivity
        )

        for (dataStoreDescription in dataStoreDescriptions) {
            sendValue(dataStoreDescription)
        }
    }

    fun getAFlow(
        informationType: informationType,
        continous: Boolean,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity? = null
    ): Flow<ArrayList<String>> {
        return flow<ArrayList<String>>
        {

            do {

                var firstResult = getAValue(
                    dateTime,
                    informationType,
                    dataStoreType.device,
                    arrayListOf(),
                    fragementActivity
                )
                emit(firstResult)

                var secondResult = getAValue(
                    dateTime,
                    informationType,
                    dataStoreType.googleSheets,
                    arrayListOf(),
                    fragementActivity
                )
                emit(secondResult)

                if (arrayStringValuesChanged(firstResult, secondResult)) {
                    val anyArrayList = arrayListOf<Any>()
                    secondResult.forEach {
                        anyArrayList.add(it)
                    }

                    val dataStoreDescription = dataStoreDescription(
                        dataStoreType.device,
                        informationType,
                        anyArrayList,
                        dateTime,
                        fragementActivity
                    )

                    sendValue(dataStoreDescription)
                }
                delay(1000)
            } while (continous)
        }
    }


    fun getActivity(
        activity: com.CBPrograms.myfitnesslogger2023.enumerations.activityType,
        dateTime: LocalDateTime,
        fragementActivity: FragmentActivity?
    ): ArrayList<String> {
        /*
            return getAValue(dateTime, informationType.activity,   arrayListOf(),fragementActivity)
        */

        return arrayListOf()
    }

    private fun getAValue(
        dateTime: LocalDateTime,
        informationType: informationType,
        storeType: dataStoreType,
        values: ArrayList<Any>,
        fragementActivity: FragmentActivity? = null
    ): ArrayList<String> {
        val dataStoreDescriptionDevice = dataStoreDescription(
            storeType,
            informationType,
            values,
            dateTime,
            fragementActivity
        )
        return getValue(dataStoreDescriptionDevice)
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
                        it.data
                    )
                }
            }

            dataStoreType.device -> {
                dataStoreDescription.getDataActions().forEach()
                {
                    sharedPrefGate.setValue(
                        dataStoreDescription.activity,
                        it.key,
                        it.value
                    )
                }
            }

            else -> {
            }
        }
    }

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

    private fun getID(): String {
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

    private fun arrayStringValuesChanged(
        firstValue: ArrayList<String>,
        secondValue: ArrayList<String>
    ): Boolean {
        var equal = true

        if (firstValue.count() == secondValue.count()) {
            for (idx in 0..firstValue.count() - 1) {
                equal = equal.and(firstValue[idx] == secondValue[idx])
            }
        } else {
            equal = false
        }

        return equal.not()
    }
}