package com.CBPrograms.myfitnesslogger2023.services

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase

object FirebaseAnalyticsService {

    fun setOnStartScreen(screen : String, context: Context)
    {
        val bundle = Bundle()
        bundle.putString("start", screen)
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    fun setOnResumeScreen(screen : String, context: Context)
    {
        val bundle = Bundle()
        bundle.putString("resume", screen)
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

}