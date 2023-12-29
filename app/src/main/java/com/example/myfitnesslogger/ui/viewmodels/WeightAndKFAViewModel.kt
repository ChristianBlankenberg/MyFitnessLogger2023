package com.example.myfitnesslogger.ui.viewmodels

import android.widget.NumberPicker
import androidx.fragment.app.FragmentActivity
import com.example.myfitnesslogger.businesslogic.*

class WeightAndKFAViewModel() : BaseViewModel() {

     init {
         initialize()
     }

    private fun initialize() {

    }

    fun initializeNumberPickers(
        weightGreatNumberPicker : NumberPicker,
        weightSmallNumberPicker : NumberPicker,
        kfaGreatNumberPicker : NumberPicker,
        kfaSmallNumberPicker : NumberPicker,
        onNoKFAValue : () -> Unit,
        onKFAValue : () -> Unit)
    {
        weightGreatNumberPicker.minValue = 72
        weightGreatNumberPicker.maxValue = 85
        weightGreatNumberPicker.value = 78

        weightSmallNumberPicker.minValue = 0
        weightSmallNumberPicker.maxValue = 9
        weightSmallNumberPicker.value = 5

        kfaGreatNumberPicker.minValue = 17
        kfaGreatNumberPicker.maxValue = 23
        kfaGreatNumberPicker.value = 21

        kfaGreatNumberPicker.setFormatter {
            when (it) {
                17 -> {
                    onNoKFAValue()
                    "-"
                }
                else -> {
                    onKFAValue()
                    it.toString()
                }
            }
        }

        kfaSmallNumberPicker.minValue = 0
        kfaSmallNumberPicker.maxValue = 9
        kfaSmallNumberPicker.value = 5
    }

    fun sendData(weightGreat : Int, weightSmall : Int, kfaGreat : Int, kfaSmall : Int, hasKFAValue : Boolean)
    {
/*
        val url = this.GetUrl()
        dataGate.sendWeight(url, weightGreat.toDouble() + weightSmall.toDouble() / 10.0)
        dataGate.sendKFA(url,kfaGreat.toDouble() + kfaSmall.toDouble() / 10.0)
 */
        val weight = getDoubleValueString(weightGreat.toDouble() + weightSmall.toDouble() / 10.0 )
        val kfa = if (hasKFAValue) getDoubleValueString(kfaGreat.toDouble() + kfaSmall.toDouble() / 10.0) else "-"
        trainingDataGate.sendCombinedWeightAndKFA(
            this.GetUrl(infoType.training),
            weight,
            kfa)

        trainingDataGate.sendTodaysDate(this.GetUrl(infoType.training), this.getTimeddmMyyyy())

        val suffix = this.getSuffix(0)

        sharedPrefGate.setValue(this.activity, "weight".plus(";").plus(suffix), weight)
        sharedPrefGate.setValue(this.activity, "kfa".plus(";").plus(suffix), kfa)
    }

    fun getTodaysWeight(): String {
        return this.getCachedDoubleValue(dataSetType.WeightKFA, "weight".plus(";").plus(this.getSuffix(0)))
    }

    fun getTodaysKFA(): String {
        return this.getCachedDoubleValue(dataSetType.WeightKFA, "kfa".plus(";").plus(this.getSuffix(0)))
    }
}