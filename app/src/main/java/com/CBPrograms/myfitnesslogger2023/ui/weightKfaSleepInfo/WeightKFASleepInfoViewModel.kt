package com.CBPrograms.myfitnesslogger2023.ui.weightKfaSleepInfo

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.CBPrograms.myfitnesslogger2023.enumerations.informationType
import com.CBPrograms.myfitnesslogger2023.ui.baseClasses.SendInfoBaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class WeightKFASleepInfoViewModel : SendInfoBaseViewModel() {

    //endregion

    //region Get functions
/*
    fun getPastKFA(deltaDays: Int, fragmentActivity : FragmentActivity? = null): Double?
    {
        return dataGate.getKFA(LocalDateTime.now().plusDays(deltaDays.toLong()), fragmentActivity)
    }
*/
/*
    fun getTodaysSleepDurationMinutes(fragementActivity: FragmentActivity? = null): String {
        val sleepDurationMinutes = dataGate.getSleepDurationMinutes(LocalDateTime.now(), fragementActivity)
        if (sleepDurationMinutes != null)
        {
            val hours = (sleepDurationMinutes.toInt().div(60))
            return "%02d".format (sleepDurationMinutes.toInt() - hours * 60)
        }
        else
        {
            return  "?"
        }
    }
    */

/*
        fun getTodaysSleepDurationHours(fragementActivity: FragmentActivity? = null): String {
        val sleepDurationMinutes = dataGate.getSleepDurationMinutes(LocalDateTime.now(), fragementActivity)
        if (sleepDurationMinutes != null)
        {
            return (sleepDurationMinutes.toInt().div(60)).toString()
        }
        else
        {
            return  "?"
        }
    }

    fun getTodaysSleepDurationMinutes(fragementActivity: FragmentActivity? = null): String {
        val sleepDurationMinutes = dataGate.getSleepDurationMinutes(LocalDateTime.now(), fragementActivity)
        if (sleepDurationMinutes != null)
        {
            val hours = (sleepDurationMinutes.toInt().div(60))
            return "%02d".format (sleepDurationMinutes.toInt() - hours * 60)
        }
        else
        {
            return  "?"
        }
    }
 */
/*
    fun getInformation(fragementActivity: FragmentActivity? = null): String {
        return dataGate.getInformation(LocalDateTime.now(), fragementActivity) ?: ""
    }

 */
    //endregion

}