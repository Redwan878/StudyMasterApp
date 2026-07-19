package com.porashona.studymaster.utils

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import java.util.Locale

class VoiceCommandManager(private val context: Context) {
    fun getSpeechIntent(): Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        // Support both Bangla and English
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "bn-BD")
        intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, arrayListOf("en-US", "bn-BD"))
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a command (e.g. Start Timer)")
        return intent
    }

    fun parseCommand(text: String): Command {
        val lower = text.lowercase(Locale.ROOT)
        return when {
            lower.contains("start") || lower.contains("শুরু") -> Command.START
            lower.contains("stop") || lower.contains("থামো") -> Command.STOP
            lower.contains("pause") || lower.contains("বিরতি") -> Command.PAUSE
            lower.contains("reset") || lower.contains("বন্ধ") -> Command.RESET
            lower.contains("music") || lower.contains("গান") -> Command.MUSIC
            else -> Command.UNKNOWN
        }
    }

    enum class Command { START, STOP, PAUSE, RESET, MUSIC, UNKNOWN }
}