package com.example.myfitnesslogger.ui.fragments

import android.R
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.myfitnesslogger.businesslogic.*
import com.example.myfitnesslogger.businesslogic.sharedPrefGate.GetUrl
import com.example.myfitnesslogger.businesslogic.alarmService
import com.example.myfitnesslogger.databinding.InfosFragmentBinding
import com.example.myfitnesslogger.ui.viewmodels.FastingViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class FastingSubFragment(val binding: InfosFragmentBinding, val host : BaseFragment, val viewModel : FastingViewModel) {

    private var isRunning: Boolean = false
    private var elapsedDuration: Duration = Duration.ZERO
    private var remainingDuration: Duration = Duration.ZERO
    private var durationHrs: Int = 0
    private var startTimeStr : String = ""
    private var endTimeStr : String = ""

    fun updateUI() {
        if (isRunning) {
            binding.containerRunning.visibility = View.VISIBLE
            binding.containerSetup.visibility = View.GONE

            binding.timeSetTextView.text = "Time Set : ".plus(durationHrs).plus("h")

            binding.timeElapsedTextView.text =
                "Time Elapsed : ".plus(getElapsedTimehhmmString())

            val remainingSeconds = Math.abs(remainingDuration.seconds)
            binding.timeRemainingTextView.text =
                "Time Remaining : ".plus(remainingSeconds / 3600).plus("h ")
                    .plus(remainingSeconds % 3600 / 60).plus("m")

            binding.startTimeTextView.text = "Start time : ".plus(startTimeStr)
            binding.endTimeTextView.text = "End time : ".plus(endTimeStr)

            val percentElapsed = (Math.abs(elapsedDuration.seconds) * 100.0) / (durationHrs * 3600.0)

            binding.percentElapsedTextView.text = "Elapsed : ".plus(String.format("%.2f", percentElapsed)).plus(" %")

            binding.pieView.ElapsedAngle = (3.6 * percentElapsed).toFloat()
        } else {
            binding.containerRunning.visibility = View.GONE
            binding.containerSetup.visibility = View.VISIBLE

            resetStopButtons()
            stopAlarm()
        }
    }

    private fun resetStopButtons() {
        this.binding.stopBtn.visibility = View.VISIBLE
        this.binding.stopBtn2.visibility = View.INVISIBLE
    }

    fun initializeUI() {
        initialize()
        start()
    }

    private fun start()
    {
        update()
        synchronizeWithExcel()
    }

    private fun update() {
        calculate()

        if (isRunning) {
        } else {
            stopAlarm()
        }

        updateUI()
    }

    private fun calculate() {
        val timeStr = sharedPrefGate.getValue(host.activity,"time")
        startTimeStr = sharedPrefGate.getValue(host.activity,"timeStamp")

        if (timeStr != "" && startTimeStr != "") {
            durationHrs = timeStr.toInt()
            val startTime = toLocalDateTime(startTimeStr)

            elapsedDuration = Duration.between(LocalDateTime.now(), startTime)

            val endTime = startTime.plusHours(durationHrs.toLong())
            endTimeStr = toString(endTime)

            remainingDuration = Duration.between(LocalDateTime.now(), endTime)
            isRunning = elapsedDuration.toHours() < durationHrs
        } else {
            isRunning = false
        }
    }

    private fun synchronizeWithExcel() {
        GlobalScope.launch {
            val url = viewModel.GetUrl(infoType.fasting)
            val currentRow = fastingDataGate.getCurrentRow(url)

            val timeStr = sharedPrefGate.getValue(host.activity,"time")
            startTimeStr = sharedPrefGate.getValue(host.activity,"timeStamp")

            if (timeStr == "" && startTimeStr == "")
            {
                // stopped
                val startTime = fastingDataGate.getStartTime(url, currentRow)
                if (startTime != "")
                {
                    var time = fastingDataGate.getTime(url, currentRow)
                    sharedPrefGate.setValue(host.activity,"time", time)
                    sharedPrefGate.setValue(host.activity,"timeStamp", startTime)

                    host.activity?.runOnUiThread {
                        this@FastingSubFragment.update()
                    }
                }
            }
            else
            {
                // running
                var stopTime = fastingDataGate.getStopTime(url, currentRow)
                if (stopTime != "")
                {
                    sharedPrefGate.setValue(host.activity,"time", "")
                    sharedPrefGate.setValue(host.activity,"timeStamp", "")

                    host.activity?.runOnUiThread {
                        this@FastingSubFragment.update()
                    }
                }
            }

            host.activity?.runOnUiThread {
                Toast.makeText(
                    host.activity, "Synchronization finished",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initialize() {

        initializeGraphicsAndContainers()
        initializeStopButtons()
        initializeDurationButtons()

        startRefreshTimer(sharedPrefGate.getValue(host.activity,"time").toIntOrNull() ?: 0)
        reStartAlarm()
    }

    private fun reStartAlarm() {
        alarmService.setAlarm(host.activity,  sharedPrefGate.getValue(host.activity, "alarmSet").toLongOrNull() ?: 0)
    }

    private fun initializeDurationButtons() {
        binding.btn1h.setOnClickListener {
            setStartAndRemainingTimeHr(1)
        }
        binding.btn4h.setOnClickListener {
            setStartAndRemainingTimeHr(4)
        }
        binding.btn6h.setOnClickListener {
            setStartAndRemainingTimeHr(6)
        }
        binding.btn8h.setOnClickListener {
            setStartAndRemainingTimeHr(8)
        }
        binding.btn10h.setOnClickListener {
            setStartAndRemainingTimeHr(10)
        }
        binding.btn12h.setOnClickListener {
            setStartAndRemainingTimeHr(12)
        }
        binding.btn14h.setOnClickListener {
            setStartAndRemainingTimeHr(14)
        }
        binding.btn16h.setOnClickListener {
            setStartAndRemainingTimeHr(16)
        }
        binding.btn18h.setOnClickListener {
            setStartAndRemainingTimeHr(18)
        }
        binding.btn20h.setOnClickListener {
            setStartAndRemainingTimeHr(20)
        }
        binding.btn22h.setOnClickListener {
            setStartAndRemainingTimeHr(22)
        }
        binding.btn24h.setOnClickListener {
            setStartAndRemainingTimeHr(24)
        }
        binding.btn36h.setOnClickListener {
            setStartAndRemainingTimeHr(36)
        }
        binding.btn48h.setOnClickListener {
            setStartAndRemainingTimeHr(48)
        }
    }

    private fun initializeStopButtons() {
        binding.stopBtn.visibility = View.VISIBLE
        binding.stopBtn2.visibility = View.INVISIBLE

        binding.stopBtn.setOnClickListener {
            binding.stopBtn.visibility = View.INVISIBLE
            binding.stopBtn2.visibility = View.VISIBLE
        }

        binding.stopBtn2.setOnClickListener {
            stopItFasting()
        }
    }

    private fun initializeGraphicsAndContainers() {
        binding.pieView.setOnClickListener {
            switchRunninfInormation()
        }

        binding.containerRunningDetails.setOnClickListener {
            switchRunninfInormation()
        }
    }

    private fun switchRunninfInormation()
    {
        if (binding.pieView.visibility == View.VISIBLE)
        {
            binding.containerRunningDetails.visibility = View.VISIBLE
            binding.pieView.visibility = View.GONE
        }
        else
        {
            binding.containerRunningDetails.visibility = View.GONE
            binding.pieView.visibility = View.VISIBLE
        }
    }

    private fun setStartAndRemainingTimeHr(remainintTimeHr: Int) {
        val remainingTimeHrStr = remainintTimeHr.toString()
        val now = LocalDateTime.now()

        sharedPrefGate.setValue(host.activity, "time", remainingTimeHrStr)
        val startTime = toString(now)
        sharedPrefGate.setValue(host.activity, "timeStamp", startTime)

        var timeInMillis = alarmService.setAlarm(host.activity, remainintTimeHr)
        sharedPrefGate.setValue(host.activity, "alarmSet", timeInMillis.toString())

        this.startRefreshTimer(remainintTimeHr)

        notificationService.showNotificationServiceFromActivity(host.activity, "Fast It!", "Fasting started ".plus(remainingTimeHrStr).plus(" h until : ".plus(toString(now.plusHours(remainintTimeHr.toLong())))), R.drawable.ic_dialog_info)

        update()

        GlobalScope.launch {
            val url = viewModel.GetUrl(infoType.fasting)
            val currentRow = fastingDataGate.getCurrentRow(url) + 1
            fastingDataGate.setCurrentRow(url, currentRow)

            fastingDataGate.sendDate(url, getTimeddmMyyyy(), currentRow)
            fastingDataGate.sendTime(url, (remainintTimeHr).toString(), currentRow)
            fastingDataGate.sendStartTime(url, startTime, currentRow)
        }
    }

    private fun startRefreshTimer(remainintTimeHr: Int) {
        if (remainintTimeHr > 0) {
            object : CountDownTimer((remainintTimeHr * 3600 * 1000).toLong(), 5000) {
                override fun onTick(millisUntilFinished: Long) {
                    //here you can have your logic to set text to edittext
                    host.activity?.runOnUiThread {
                        this@FastingSubFragment.update()
                    }
                    val isRunning = this@FastingSubFragment.isRunning
                    if (isRunning.not()) {
                        this.cancel()
                    }
                }

                override fun onFinish() {
                }
            }.start()
        }
    }

    private fun stopItFasting() {
        notificationService.showNotificationServiceFromActivity(host.activity, "Fast It!", "Fasting stopped : ".plus(toString(
            LocalDateTime.now()).plus(" after ").plus(getElapsedTimehhmmString()).plus(" / ").plus(sharedPrefGate.getValue(host.activity, "time").plus(" h"))), R.drawable.ic_dialog_info)

        sharedPrefGate.setValue(host.activity,"time", "")
        sharedPrefGate.setValue(host.activity,"timeStamp", "")

        stopAlarm()
        update()

        GlobalScope.launch {
            val url = viewModel.GetUrl(infoType.fasting)
            val currentRow = fastingDataGate.getCurrentRow(url) + 1
            fastingDataGate.setCurrentRow(url, currentRow)

            fastingDataGate.sendDate(url, getTimeddmMyyyy(), currentRow)
            fastingDataGate.sendElapsedTime(url, elapsedSeconds = Math.abs(elapsedDuration.seconds).toString(), currentRow)
            fastingDataGate.sendStopTime(url, getTimeHHmm(), currentRow)
        }
    }

    private fun getElapsedTimehhmmString() : String{
        val elapsedSeconds = Math.abs(elapsedDuration.seconds)
        return (elapsedSeconds / 3600).toString().plus("h ")
            .plus(elapsedSeconds % 3600 / 60).plus("m")
    }

    private fun stopAlarm()
    {
        alarmService.cancelAlarm(host.activity)
        sharedPrefGate.setValue(host.activity, "alarmSet", "")
    }
}