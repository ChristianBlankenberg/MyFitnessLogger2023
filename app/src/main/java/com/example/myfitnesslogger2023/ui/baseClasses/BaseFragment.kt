package com.example.myfitnesslogger2023.ui.baseClasses

import androidx.fragment.app.Fragment
import com.example.myfitnesslogger2023.services.FirebaseAnalyticsService

open class BaseFragment : Fragment(){

    override fun onStart() {
        super.onStart()

        FirebaseAnalyticsService.setOnStartScreen(this.javaClass.name, requireContext())

    }

    override fun onResume() {
        super.onResume()

        FirebaseAnalyticsService.setOnResumeScreen(this.javaClass.name, requireContext())
    }
}