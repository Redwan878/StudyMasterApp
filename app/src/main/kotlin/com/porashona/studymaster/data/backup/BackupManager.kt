package com.porashona.studymaster.data.backup

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * JSON export / import of all user-generated data in the app.
 *
 * Covers every entity the user creates — sessions, subjects, routines,
 * achievements (with progress), profile, goals, tasks, notes, exams,
 * challenges, blocked-apps, quotes, resources, academic-events.
 *
 * Format is a single top-level JSON object (see [BackupPayload]). We keep
 * it dead-simple (no migration table) — backups include the schema
 * [version] so if we later add fields we can still import older backups.
 */
object BackupManager {

    private const val BACKUP_VERSION = 1

    data class BackupPayload(
        val version: Int = BACKUP_VERSION,
        val createdAt: Long = System.currentTimeMillis(),
        val sessions: List<StudySession> = emptyList(),
        val subjects: List<Subject> = emptyList(),
        val routines: List<Routine> = emptyList(),
        val achievements: List<Achievement> = emptyList(),
        val profile: UserProfile? = null,
        val goals: List<Goal> = emptyList(),
        val tasks: List<Task> = emptyList(),
        val notes: List<Note> = emptyList(),
        val exams: List<Exam> = emptyList(),
        val challenges: List<Challenge> = emptyList(),
        val blockedApps: List<BlockedApp> = emptyList(),
        val quotes: List<Quote> = emptyList(),
        val resources: List<StudyResource> = emptyList(),
        val academicEvents: List<AcademicEvent> = emptyList(),
    )

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun export(context: Context): String = withContext(Dispatchers.IO) {
        val app = context.applicationContext as StudyMasterApplication
        val db = app.database

        val payload = BackupPayload(
            sessions = db.studySessionDao().getAllSessions().first(),
            subjects = db.subjectDao().getAllSubjects().first(),
            routines = db.routineDao().getAllRoutines().first(),
            achievements = db.achievementDao().getAllAchievements().first(),
            profile = db.userProfileDao().getProfile().first(),
            goals = db.goalDao().getAllGoals().first(),
            tasks = db.taskDao().getAllTasks().first(),
            notes = db.noteDao().getAllNotes().first(),
            exams = db.examDao().getAllExams().first(),
            challenges = runCatching { db.challengeDao().getAllChallenges().first() }.getOrDefault(emptyList()),
            blockedApps = runCatching { db.blockedAppDao().getAllBlockedApps().first() }.getOrDefault(emptyList()),
            quotes = runCatching { db.quoteDao().getAllQuotes().first() }.getOrDefault(emptyList()),
            resources = runCatching { db.studyResourceDao().getAllResources().first() }.getOrDefault(emptyList()),
            academicEvents = runCatching { db.academicEventDao().getAllEvents().first() }.getOrDefault(emptyList()),
        )
        gson.toJson(payload)
    }

    /**
     * Writes the current app state to [targetUri] (typically chosen by the user
     * via ACTION_CREATE_DOCUMENT). Returns the number of bytes written.
     */
    suspend fun exportToUri(context: Context, targetUri: Uri): Int = withContext(Dispatchers.IO) {
        val json = export(context)
        context.contentResolver.openOutputStream(targetUri)?.use { os ->
            os.write(json.toByteArray(Charsets.UTF_8))
            os.flush()
        } ?: throw IllegalStateException("Could not open $targetUri for writing")
        json.length
    }

    /**
     * Loads a backup file, wipes current DB content, and re-inserts everything
     * from the backup. Destructive — caller should have confirmed with the
     * user already.
     */
    suspend fun importFromUri(context: Context, sourceUri: Uri): ImportResult = withContext(Dispatchers.IO) {
        val app = context.applicationContext as StudyMasterApplication
        val db = app.database

        val json = context.contentResolver.openInputStream(sourceUri)?.use { stream ->
            BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).readText()
        } ?: throw IllegalStateException("Could not open $sourceUri for reading")

        val payload = gson.fromJson(json, BackupPayload::class.java)
            ?: throw IllegalArgumentException("Not a StudyMaster backup file")

        // Clear existing content first so the restored state is exact.
        db.studySessionDao().deleteAll()
        db.subjectDao().deleteAll()
        db.routineDao().deleteAll()
        // Achievements are re-seeded by initializeAchievements; import overwrites.
        db.achievementDao().deleteAll()
        db.goalDao().deleteAll()
        db.taskDao().deleteAll()
        db.noteDao().deleteAll()
        db.examDao().deleteAll()

        payload.subjects.forEach { db.subjectDao().insert(it) }
        payload.sessions.forEach { db.studySessionDao().insert(it) }
        payload.routines.forEach { db.routineDao().insert(it) }
        payload.achievements.forEach { db.achievementDao().insert(it) }
        payload.profile?.let { db.userProfileDao().insert(it) }
        payload.goals.forEach { db.goalDao().insert(it) }
        payload.tasks.forEach { db.taskDao().insert(it) }
        payload.notes.forEach { db.noteDao().insert(it) }
        payload.exams.forEach { db.examDao().insert(it) }

        ImportResult(
            version = payload.version,
            createdAt = payload.createdAt,
            sessions = payload.sessions.size,
            subjects = payload.subjects.size,
            tasks = payload.tasks.size,
            notes = payload.notes.size,
            goals = payload.goals.size,
            exams = payload.exams.size,
        )
    }

    data class ImportResult(
        val version: Int,
        val createdAt: Long,
        val sessions: Int,
        val subjects: Int,
        val tasks: Int,
        val notes: Int,
        val goals: Int,
        val exams: Int,
    )
}
