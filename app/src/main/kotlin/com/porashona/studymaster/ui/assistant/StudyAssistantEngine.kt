package com.porashona.studymaster.ui.assistant

import com.porashona.studymaster.data.model.Goal
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.UserProfile
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Local, heuristic-only "study assistant". Produces a small list of suggestions
 * given the user's study history, subjects, goals, tasks and current profile.
 *
 * Intentionally no network calls / no LLM — the spec asks for an AI assistant,
 * but shipping an LLM integration is out of scope for this PR (needs an API key
 * and a usage policy). This engine gives deterministic, offline suggestions
 * that are still useful, and can be replaced with a real LLM backend later
 * without touching the UI.
 */
object StudyAssistantEngine {

    data class Suggestion(
        val title: String,
        val body: String,
        val kind: Kind
    )

    enum class Kind { NEXT, INSIGHT, NUDGE }

    data class Input(
        val profile: UserProfile?,
        val subjects: List<Subject>,
        val recentSessions: List<StudySession>, // last ~14 days
        val openTasks: List<Task>,
        val activeGoals: List<Goal>
    )

    fun suggest(input: Input): List<Suggestion> =
        listOfNotNull(
            nextSubjectSuggestion(input),
            streakNudge(input.profile),
            productivityInsight(input.recentSessions),
            taskBacklogNudge(input.openTasks),
            goalProgressInsight(input.activeGoals)
        )

    // ---- individual rules ------------------------------------------------

    private fun nextSubjectSuggestion(input: Input): Suggestion? {
        if (input.subjects.isEmpty()) return null

        val weekAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        val recent = input.recentSessions.filter { it.startTime.time >= weekAgo }
        val timeBySubject = recent
            .filter { it.subjectId > 0 }
            .groupBy { it.subjectId }
            .mapValues { (_, sessions) -> sessions.sumOf { it.durationInSeconds } }

        // Pick the subject with the *least* recorded time this week —
        // encourages balance. If everything is zero, default to the first one.
        val target = input.subjects.minByOrNull { timeBySubject[it.id] ?: 0L }
            ?: return null
        val minutesStudied = ((timeBySubject[target.id] ?: 0L) / 60)

        val body = if (minutesStudied == 0L) {
            "এই সপ্তাহে \"${target.name}\" একেবারেই পড়া হয়নি। ২৫ মিনিটের একটি সেশন শুরু করো।"
        } else {
            "এই সপ্তাহে \"${target.name}\" সবচেয়ে কম পড়া হয়েছে (${minutesStudied} মিনিট)। একটু সময় দাও।"
        }
        return Suggestion(
            title = "\"${target.name}\" এ ফোকাস",
            body = body,
            kind = Kind.NEXT
        )
    }

    private fun streakNudge(profile: UserProfile?): Suggestion? {
        profile ?: return null
        if (profile.currentStreak <= 0) return null
        val todayStart = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val lastStudy = profile.lastStudyDate ?: return null
        if (lastStudy in 1..(todayStart - 1)) {
            return Suggestion(
                title = "স্ট্রিক রক্ষা করো",
                body = "আজ এখনও পড়োনি। তোমার ${profile.currentStreak} দিনের স্ট্রিক অক্ষুণ্ণ রাখতে অন্তত একটি সেশন সম্পন্ন করো।",
                kind = Kind.NUDGE
            )
        }
        return null
    }

    private fun productivityInsight(sessions: List<StudySession>): Suggestion? {
        if (sessions.size < 3) return null
        val lastWeek = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        val prevWeek = lastWeek - TimeUnit.DAYS.toMillis(7)
        val thisWeekMinutes = sessions.filter { it.startTime.time >= lastWeek }
            .sumOf { it.durationInSeconds } / 60
        val prevWeekMinutes = sessions.filter { it.startTime.time in prevWeek until lastWeek }
            .sumOf { it.durationInSeconds } / 60

        if (prevWeekMinutes <= 0) return null
        val delta = thisWeekMinutes - prevWeekMinutes
        return if (delta > 0) {
            Suggestion(
                title = "সপ্তাহের অগ্রগতি",
                body = "গত সপ্তাহের তুলনায় তুমি এই সপ্তাহে ${delta} মিনিট বেশি পড়েছ। দারুণ ছন্দে আছ!",
                kind = Kind.INSIGHT
            )
        } else if (delta < 0) {
            Suggestion(
                title = "সপ্তাহের অগ্রগতি",
                body = "গত সপ্তাহের তুলনায় তুমি এই সপ্তাহে ${-delta} মিনিট কম পড়েছ। ছন্দে ফিরতে চেষ্টা করো।",
                kind = Kind.INSIGHT
            )
        } else null
    }

    private fun taskBacklogNudge(openTasks: List<Task>): Suggestion? {
        if (openTasks.isEmpty()) return null
        val now = System.currentTimeMillis()
        val endOfToday = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis
        val overdue = openTasks.count { it.dueDate?.let { d -> d in 1 until now } == true }
        val dueToday = openTasks.count { it.dueDate?.let { d -> d in now..endOfToday } == true }
        return when {
            overdue > 0 -> Suggestion(
                title = "$overdue টি টাস্ক ডেডলাইন পার করেছে",
                body = "টাস্ক স্ক্রীনে যাও আর মেয়াদোত্তীর্ণ টাস্কগুলো আগে শেষ করো।",
                kind = Kind.NUDGE
            )
            dueToday > 0 -> Suggestion(
                title = "আজ $dueToday টি টাস্ক জমা দিতে হবে",
                body = "টাইমার চালু করে এখনই শুরু করে দাও।",
                kind = Kind.NUDGE
            )
            else -> null
        }
    }

    private fun goalProgressInsight(goals: List<Goal>): Suggestion? {
        val active = goals.filter { !it.isCompleted }
        if (active.isEmpty()) return null
        val near = active.filter {
            it.targetMinutes > 0 && it.currentMinutes.toDouble() / it.targetMinutes >= 0.8
        }
        return if (near.isNotEmpty()) {
            val g = near.first()
            val pct = ((g.currentMinutes.toDouble() / g.targetMinutes) * 100).toInt()
            Suggestion(
                title = "\"${g.title}\" প্রায় শেষ",
                body = "এই লক্ষ্যটা ${pct}% সম্পন্ন। একটু ঠেলে দিলেই আনলক হবে!",
                kind = Kind.INSIGHT
            )
        } else null
    }
}
