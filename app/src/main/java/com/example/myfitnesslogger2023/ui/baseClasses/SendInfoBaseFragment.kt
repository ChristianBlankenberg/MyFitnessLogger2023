package com.example.myfitnesslogger2023.ui.baseClasses

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.myfitnesslogger.services.keyboardService
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class SendInfoBaseFragment : BaseFragment() {
    private lateinit var viewModel : SendInfoBaseViewModel
    private var sendButton : Button? = null
    private var circularProgressIndicator : CircularProgressIndicator? = null

    fun initialize(
        viewModel : SendInfoBaseViewModel,
        sendButton : Button?,
        circularProgressIndicator : CircularProgressIndicator?)
    {
        this.viewModel = viewModel
        this.viewModel.initialize(this.requireActivity())

        this.sendButton = sendButton

        this.circularProgressIndicator = circularProgressIndicator

        this.initializeSendButton()
    }

    fun showYesNoDialog(question : String, positiveFun : () -> Unit) : Boolean
    {
        var result : Int = DialogInterface.BUTTON_NEUTRAL
        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                positiveFun()
            }
        }

        val builder = AlertDialog.Builder(context);
        builder.setMessage(question).setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()

        return true //== DialogInterface.BUTTON_POSITIVE
    }

    protected fun initializeALabel(aLabel: TextView?, labelID: Int, todaysValue: String)
    {
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

    protected fun initializeDurationNumberPickers(hourNP : NumberPicker, minuteNP : NumberPicker)
    {
        hourNP.maxValue = 23
        hourNP.minValue = 0
        hourNP.value = 8

        minuteNP.maxValue = 59
        minuteNP.minValue = 0
        minuteNP.value = 0
    }

    override fun onResume() {
        super.onResume()

        this.reInitializeLabels()
        this.sendButton?.isEnabled = this.viewModel.canSendData()
    }

    private fun initializeSendButton()
    {
        this.sendButton?.setOnClickListener {
            this.sendButton?.isEnabled = false

            keyboardService.hideKeyboard()
            sendPreAction()

            GlobalScope.launch {
                activity?.runOnUiThread {
                    this@SendInfoBaseFragment.circularProgressIndicator?.visibility = View.VISIBLE
                }

                sendAction()

                activity?.runOnUiThread {
                    this@SendInfoBaseFragment.circularProgressIndicator?.visibility = View.INVISIBLE
                    this@SendInfoBaseFragment.reInitializeLabels()
                    this@SendInfoBaseFragment.sendButton?.isEnabled = true
                }
            }
        }
    }

    abstract fun sendPreAction();

    abstract fun sendAction();

    abstract fun reInitializeLabels();
}