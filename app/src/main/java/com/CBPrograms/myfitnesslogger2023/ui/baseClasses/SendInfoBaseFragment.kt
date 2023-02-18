package com.CBPrograms.myfitnesslogger2023.ui.baseClasses

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import com.CBPrograms.myfitnesslogger2023.services.keyboardService
import com.CBPrograms.myfitnesslogger2023.utils.mathFunctions
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class SendInfoBaseFragment : BaseFragment() {
    private lateinit var viewModel: SendInfoBaseViewModel
    private var sendButton: Button? = null
    private var circularProgressIndicator: CircularProgressIndicator? = null

    override fun onStart() {
        this.initializeUI()
        this.initializeFlows()
        super.onStart()
    }

    fun initialize(
        viewModel: SendInfoBaseViewModel,
        sendButton: Button?,
        circularProgressIndicator: CircularProgressIndicator?
    ) {
        this.viewModel = viewModel
        this.viewModel.initialize(this.requireActivity())

        this.sendButton = sendButton

        this.circularProgressIndicator = circularProgressIndicator

        this.initializeSendButton()
    }

    fun showYesNoDialog(question: String, positiveFun: () -> Unit): Boolean {
        var result: Int = DialogInterface.BUTTON_NEUTRAL
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                positiveFun()
            }
        }

        val builder = AlertDialog.Builder(context);
        builder.setMessage(question).setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()

        return true //== DialogInterface.BUTTON_POSITIVE
    }

    protected fun getYesterdaysTodaysValueInformation(
        todaysValue: Double?,
        yesterdaysValue: Double?
    ): String {
        val result =
            if (todaysValue != null) {
                if (yesterdaysValue != null) {
                    val sign = if (yesterdaysValue < todaysValue) "+" else "-"
                    todaysValue.toShortString().plus(" / ").plus(sign).plus(" ")
                        .plus(Math.abs(yesterdaysValue - todaysValue).toShortString())
                } else {
                    todaysValue.toShortString().plus(" / ?")
                }
            } else {
                if (yesterdaysValue != null) {
                    "? / ".plus(yesterdaysValue.toShortString())
                } else {
                    "?"
                }
            }

        return result
    }

    protected fun observeTodaysYesterDaysDoubleFlowsAndSetControls(
        todaysFlow : Flow<ArrayList<String>>,
        yesterdaysFlow : Flow<ArrayList<String>>,
        labelID: Int,
        aLabel: TextView?,
        preCommaNumberPicker : NumberPicker?,
        pastCommaNumberPicker : NumberPicker?)
    {
        var todaysValue: Double? = null;
        var yesterdaysValue: Double? = null;

        fun checkTodayYesterdayLabel() {
            activity?.runOnUiThread {
                if (todaysValue != null && yesterdaysValue != null) {

                    this@SendInfoBaseFragment.initializeALabel(
                        aLabel,
                        labelID,
                        getYesterdaysTodaysValueInformation(todaysValue, yesterdaysValue))
                }
                else if (todaysValue != null && yesterdaysValue == null) {
                    this@SendInfoBaseFragment.initializeALabel(
                        aLabel,
                        labelID,
                        todaysValue.toString())
                }
            }
        }

        GlobalScope.launch {

            todaysFlow.collect {
                activity?.runOnUiThread {
                    todaysValue = it.firstOrNull()?.toDoubleOrNull()
                    var todaysWeightPreAndPastComma =
                        mathFunctions.getPreAndPastCommaValue(todaysValue ?: 0.0)

                    preCommaNumberPicker?.value =
                        todaysWeightPreAndPastComma!!.first
                    pastCommaNumberPicker?.value =
                        todaysWeightPreAndPastComma!!.second

                    checkTodayYesterdayLabel()
                }
            }
        }

        GlobalScope.launch {
            yesterdaysFlow.collect {
                activity?.runOnUiThread {
                    yesterdaysValue = it.firstOrNull()?.toDoubleOrNull()
                    checkTodayYesterdayLabel()
                }
            }
        }
    }

    protected fun observeTodaysDoubleFlowsAndSetControls(
        todaysFlow : Flow<ArrayList<String>>,
        labelID: Int,
        aLabel: TextView?,
        preCommaNumberPicker : NumberPicker?,
        pastCommaNumberPicker : NumberPicker?)
    {
        GlobalScope.launch {

            todaysFlow.collect {
                activity?.runOnUiThread {
                    val todaysValue = it.firstOrNull()

                    this@SendInfoBaseFragment.initializeALabel(
                        aLabel,
                        labelID,
                        todaysValue ?: "?")

                    if (preCommaNumberPicker != null && pastCommaNumberPicker != null) {
                        val todaysWeightPreAndPastComma =
                            mathFunctions.getPreAndPastCommaValue(todaysValue?.toDoubleOrNull() ?: 0.0)

                        todaysWeightPreAndPastComma!!.first
                        pastCommaNumberPicker?.value =
                            todaysWeightPreAndPastComma!!.second

                        preCommaNumberPicker?.value = todaysWeightPreAndPastComma.first
                        pastCommaNumberPicker?.value = todaysWeightPreAndPastComma.second
                    }
                    else if (preCommaNumberPicker != null && pastCommaNumberPicker == null)
                    {
                        preCommaNumberPicker?.value = todaysValue?.toIntOrNull() ?: 0
                    }
                }
            }
        }
    }

    protected fun observeYesterdaysIntFlowAndSetControls(
        yesterdaysFlow : Flow<ArrayList<String>>,
        labelID: Int,
        aLabel: TextView?,
        valueNp : NumberPicker?) {
        GlobalScope.launch {
            yesterdaysFlow.collect {
                activity?.runOnUiThread {
                    val intValue = it.firstOrNull()?.toIntOrNull() ?: 0

                    valueNp?.value = intValue ?: 0

                    this@SendInfoBaseFragment.initializeALabel(
                        aLabel,
                        labelID,
                        intValue.toString())
                }
            }
        }
    }

    protected fun observeTodaysDurationFlowAndSetControls(
        todaysFlow : Flow<ArrayList<String>>,
        labelID: Int,
        aLabel: TextView?,
        hourNumberPicker : NumberPicker?,
        minutesNumberPicker : NumberPicker?)
    {
        GlobalScope.launch {
            var minutes: Int? = null;

            todaysFlow.collect {
                activity?.runOnUiThread {
                    minutes = it.firstOrNull()?.toIntOrNull()

                    val durationHoursAndMinutes =
                        mathFunctions.getHoursAndMinutes(minutes ?: 0)

                    hourNumberPicker?.value =
                        durationHoursAndMinutes.first.toInt()
                    minutesNumberPicker?.value =
                        durationHoursAndMinutes.second.toInt()

                    this@SendInfoBaseFragment.initializeALabel(
                        aLabel,
                        labelID,
                        durationHoursAndMinutes.first + ":" + durationHoursAndMinutes.second)
                }
            }
        }
    }

    protected fun observeTodaysStringFlowAndSetControls(
        todaysFlow : Flow<ArrayList<String>>,
        labelID: Int,
        aLabel: TextView?)
    {
        GlobalScope.launch {
            var information: String = "";

            todaysFlow.collect {
                activity?.runOnUiThread {
                    information = it.first()

                    this@SendInfoBaseFragment.initializeALabel(
                        aLabel,
                        labelID,
                        information)
                }
            }
        }
    }

    protected fun initializeALabel(aLabel: TextView?, labelID: Int, todaysValue: String) {
        if (aLabel != null) {
            if (todaysValue == "") {
                aLabel.setText(labelID)
            } else {
                var labelText = getContext()?.getResources()?.getText(labelID)
                labelText = labelText?.toString().plus(" (").plus(todaysValue).plus(")")
                aLabel.setText(labelText ?: "error")
            }
        }
    }

    protected fun Double.toShortString(): String {
        return (Math.round(this * 100) / 100.0).toString()
    }

    protected fun initializeDurationNumberPickers(hourNP: NumberPicker, minuteNP: NumberPicker) {
        hourNP.maxValue = 23
        hourNP.minValue = 0
        hourNP.value = 8

        minuteNP.maxValue = 59
        minuteNP.minValue = 0
        minuteNP.value = 0
    }

    override fun onResume() {
        super.onResume()

        try {
        } catch (e: Exception) {
        }

        this.sendButton?.isEnabled = this.viewModel.canSendData()
    }

    private fun initializeSendButton() {
        this.sendButton?.setOnClickListener {
            this.sendButton?.isEnabled = false

            keyboardService.hideKeyboard()
            sendPreAction()

            GlobalScope.launch {
                activity?.runOnUiThread {
                    this@SendInfoBaseFragment.sendButton?.isEnabled = false
                    this@SendInfoBaseFragment.circularProgressIndicator?.visibility = View.VISIBLE
                }

                sendAction()

                activity?.runOnUiThread {
                    this@SendInfoBaseFragment.circularProgressIndicator?.visibility = View.INVISIBLE
                    this@SendInfoBaseFragment.sendButton?.isEnabled = true
                }

                initializeFlows()
            }
        }
    }

    abstract fun sendPreAction();

    abstract fun sendAction();

    abstract fun initializeUI();

    abstract fun initializeFlows();
}