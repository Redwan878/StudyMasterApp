package com.porashona.studymaster.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.utils.NotificationHelper

class RoutineAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("RoutineAlarmReceiver", "Routine alarm triggered")

        // Get routine details from intent
        val routineTitle = intent.getStringExtra("routine_title") ?: "রুটিন"
        val routineTime = intent.getStringExtra("routine_time") ?: ""

        // Show notification using NotificationHelper
        NotificationHelper(context.applicationContext).showRoutineNotification(
            title = routineTitle,
            time = routineTime
        )
    }
}
