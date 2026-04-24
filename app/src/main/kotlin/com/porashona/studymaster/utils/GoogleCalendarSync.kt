package com.porashona.studymaster.utils

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast
import com.porashona.studymaster.R

/**
 * Lightweight Google Calendar integration via the public `CalendarContract`
 * intent API — no OAuth, no account picker, no Google Play Services. We
 * can't read the user's calendar without runtime contact/calendar
 * permissions (intentionally out of scope), but we can:
 *
 *  - Open the Calendar app to a specific day.
 *  - Pre-fill an "Add event" sheet with a StudyMaster session.
 *
 * Everything else is left as a hook for a future real sync integration.
 */
object GoogleCalendarSync {

    fun openCalendarAt(context: Context, millis: Long) {
        val builder = CalendarContract.CONTENT_URI.buildUpon().appendPath("time")
        ContentUris.appendId(builder, millis)
        val intent = Intent(Intent.ACTION_VIEW)
            .setData(builder.build())
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startSafely(context, intent)
    }

    fun createStudyEvent(
        context: Context,
        title: String,
        startMillis: Long,
        durationMinutes: Int,
        description: String = "",
    ) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.DESCRIPTION, description)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(
                CalendarContract.EXTRA_EVENT_END_TIME,
                startMillis + durationMinutes * 60L * 1000L
            )
            .putExtra(CalendarContract.Events.HAS_ALARM, true)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startSafely(context, intent)
    }

    private fun startSafely(context: Context, intent: Intent) {
        runCatching { context.startActivity(intent) }
            .onFailure {
                Toast.makeText(
                    context,
                    R.string.google_calendar_not_installed,
                    Toast.LENGTH_LONG,
                ).show()
            }
    }
}
