package com.example.myfitnesslogger.businesslogic

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

class httpRequestService {

    fun sendGet(url: String, parameter : String) : String {
        val url = URL(url.plus("?").plus(parameter))

        var result : String = ""

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET

            Log.d("httpRequestService.sendGet", "\nSent 'GET' request to URL : $url; Response Code : $responseCode")

            inputStream.bufferedReader().use {
                result = it.readText()
            }
        }

        return result
    }
}