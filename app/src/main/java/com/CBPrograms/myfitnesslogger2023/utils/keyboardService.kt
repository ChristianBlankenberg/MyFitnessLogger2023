package com.CBPrograms.myfitnesslogger2023.services

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

object keyboardService {

    private var mainActivity : AppCompatActivity? = null

    fun initialize(mainActivity : AppCompatActivity)
    {
        this.mainActivity = mainActivity
    }

    fun hideKeyboard()
    {
        this.mainActivity?.currentFocus?.let { view ->
            val imm = this.mainActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}