package com.porashona.studymaster.data.model

data class TimerMode(
    val id: String,
    val name: String,
    val nameBn: String,
    val workDuration: Int, // minutes
    val shortBreakDuration: Int,
    val longBreakDuration: Int,
    val pomodorosUntilLongBreak: Int,
    val icon: String,
    val description: String,
    val descriptionBn: String
)

object TimerModes {
    val CLASSIC_POMODORO = TimerMode(
        id = "classic",
        name = "Classic Pomodoro",
        nameBn = "ক্লাসিক পোমোডোরো",
        workDuration = 25,
        shortBreakDuration = 5,
        longBreakDuration = 15,
        pomodorosUntilLongBreak = 4,
        icon = "🍅",
        description = "Traditional 25/5 technique",
        descriptionBn = "ঐতিহ্যবাহী ২৫/৫ টেকনিক"
    )

    val LONG_POMODORO = TimerMode(
        id = "long",
        name = "Long Focus",
        nameBn = "দীর্ঘ ফোকাস",
        workDuration = 50,
        shortBreakDuration = 10,
        longBreakDuration = 30,
        pomodorosUntilLongBreak = 2,
        icon = "🎯",
        description = "Extended 50/10 sessions",
        descriptionBn = "বর্ধিত ৫০/১০ সেশন"
    )

    val RULE_52_17 = TimerMode(
        id = "52_17",
        name = "52/17 Rule",
        nameBn = "৫২/১৭ নিয়ম",
        workDuration = 52,
        shortBreakDuration = 17,
        longBreakDuration = 30,
        pomodorosUntilLongBreak = 3,
        icon = "⚡",
        description = "Productivity research based",
        descriptionBn = "প্রোডাক্টিভিটি গবেষণা ভিত্তিক"
    )

    val FLOW_MODE = TimerMode(
        id = "flow",
        name = "Flow Mode",
        nameBn = "ফ্লো মোড",
        workDuration = 90,
        shortBreakDuration = 0,
        longBreakDuration = 20,
        pomodorosUntilLongBreak = 1,
        icon = "🌊",
        description = "No interruptions, deep work",
        descriptionBn = "কোনো বিরতি নেই, গভীর কাজ"
    )

    val SHORT_BURST = TimerMode(
        id = "short",
        name = "Short Burst",
        nameBn = "শর্ট বার্স্ট",
        workDuration = 15,
        shortBreakDuration = 3,
        longBreakDuration = 10,
        pomodorosUntilLongBreak = 6,
        icon = "⚡",
        description = "Quick focused sessions",
        descriptionBn = "দ্রুত ফোকাসড সেশন"
    )

    val STOPWATCH = TimerMode(
        id = "stopwatch",
        name = "Stopwatch",
        nameBn = "স্টপওয়াচ",
        workDuration = 0, // Unlimited
        shortBreakDuration = 0,
        longBreakDuration = 0,
        pomodorosUntilLongBreak = 0,
        icon = "⏱️",
        description = "Track time without limits",
        descriptionBn = "সীমাহীন সময় ট্র্যাক করুন"
    )

    val allModes = listOf(
        CLASSIC_POMODORO,
        LONG_POMODORO,
        RULE_52_17,
        FLOW_MODE,
        SHORT_BURST,
        STOPWATCH
    )

    fun getById(id: String): TimerMode = allModes.find { it.id == id } ?: CLASSIC_POMODORO
}