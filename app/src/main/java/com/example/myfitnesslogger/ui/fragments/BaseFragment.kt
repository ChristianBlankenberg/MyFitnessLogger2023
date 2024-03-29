package com.example.myfitnesslogger.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myfitnesslogger.businesslogic.infoType
import com.example.myfitnesslogger.ui.viewmodels.BaseViewModel

open class BaseFragment() : Fragment() {

    private lateinit var viewModel : BaseViewModel

    fun initialize(viewModel : BaseViewModel)
    {
        this.viewModel = viewModel
        this.viewModel.initialize(this.requireActivity())
    }

    fun showYesNoDialog(question : String, positiveFun : () -> Unit) : Boolean{
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

    protected fun isConnectionUrlDefined(type : infoType) : Boolean
    {
        return this.viewModel.GetUrl(type) != ""
    }

    protected fun initializeALabel(aLabel: TextView, labelID: Int, todaysValue: String) {
        if (todaysValue == "") {
            aLabel.setText(labelID)
        }
        else
        {
            var labelText = getContext()?.getResources()?.getText(labelID)
            labelText = labelText?.toString().plus(" (").plus(todaysValue).plus(")")
            aLabel.setText(labelText ?: "error")
        }
    }
}