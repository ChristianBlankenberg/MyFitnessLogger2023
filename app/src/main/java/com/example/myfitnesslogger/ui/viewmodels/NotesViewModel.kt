package com.example.myfitnesslogger.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import com.example.myfitnesslogger.businesslogic.*

class NotesViewModel() : BaseViewModel() {
    fun sendComment(remarks : String, inBerga : Boolean, noTraining : Boolean) {
        val url = this.GetUrl(infoType.training)

        val remarkToSend = (if (inBerga) "Berga, " else "").plus(if (noTraining) "trainingsfrei, " else "").plus(remarks)

        trainingDataGate.sendRemarks(url,remarkToSend)
        trainingDataGate.sendTodaysDate(url, this.getTimeddmMyyyy())

        sharedPrefGate.setValue(this.activity, "remarks".plus(this.getSuffix(0)), remarkToSend)
    }

    fun getComment() : String
    {
        return trainingDataGate.getRemarks(this.GetUrl(infoType.training));
    }

    fun getCachedComment() : String
    {
        return this.getCachedValue(dataSetType.Remarks, "remarks".plus(this.getSuffix(0)))
    }
}