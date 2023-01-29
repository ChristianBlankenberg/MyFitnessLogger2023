package com.CBPrograms.myfitnesslogger2023.services

import com.google.firebase.crashlytics.FirebaseCrashlytics

object FirebaseCrashlyticsService {

    fun log(message : String)
    {
        FirebaseCrashlytics.getInstance().log(message)
        FirebaseCrashlytics.getInstance().sendUnsentReports()
    }

    fun reportCrash(exception : Exception)
    {
        FirebaseCrashlytics.getInstance().recordException(exception)
        FirebaseCrashlytics.getInstance().sendUnsentReports()
    }

}