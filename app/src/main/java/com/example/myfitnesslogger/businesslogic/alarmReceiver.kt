package com.example.myfitnesslogger.businesslogic
import android.os.Vibrator
import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context

class alarmReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            Toast.makeText(
                context, "Times Up... Enjoy your meal !!!",
                Toast.LENGTH_LONG
            ).show()

            val vibrator = context
                .getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            vibrator.vibrate(2000)


        }
    }