package com.porashona.studymaster.data.sync

import android.content.Context
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.Date

/**
 * SyncDataExporter - Handles exporting local data to JSON format for syncing
 * Provides methods to export different entity types for cloud synchronization
 */
class SyncDataExporter(private val context: Context) {

    private val application: StudyMasterApplication
        get() = context.applicationContext as StudyMasterApplication

    private val db
        get() = application.database

    // Timestamp for consistent sync tracking
    private var syncTimestamp: Long = System.currentTimeMillis()

    data class ExportResult(
        val success: Boolean,
        val itemsExported: Int,
        val exportData: JSONObject
    )

    /**
     * Export all data in standard format
     */
    fun exportAll(): ExportResult = withContext(Dispatchers.IO) {
        val result = JSONObject()

        try {
            val sessions = db.studySessionDao().getAllSessions().first()
            val sessionList = result.getJSONArray("sessions") ?: JSONArray()

            for (session in sessions) {
                sessionList.put(session.toSyncMap())
            }

            result.put("sessions", sessionList)
            result.put("timestamp", syncTimestamp)
            result.put("version", 1)

            ExportResult(true, sessions.size, result)

        } catch (e: Exception) {
            result.put("error", e.message)
            ExportResult(false, 0, result)
        }
    }

    /**
     * Export study sessions for sync
     */
    fun exportSessions(): List<Map<String, Any?>> = withContext(Dispatchers.IO) {
        val sessions = db.studySessionDao().getAllSessions().first()
        sessions.map { it.toSyncMap() }
    }

    /**
     * Export routines for sync
     */
    fun exportRoutines(): List<Map<String, Any?>> = withContext(Dispatchers.IO) {
        val routines = db.routineDao().getAllRoutines().first()
        routines.map { it.toSyncMap() }
    }

    /**
     * Export preferences for sync
     */
    fun exportPreferences(): Map<String, Any> {
        val prefs = context.getSharedPreferences("study_prefs", Context.MODE_PRIVATE)
        return prefs.all as Map<String, Any>
    }

    /**
     * Export profile for sync
     */
    fun exportProfile(): UserProfile {
        return runCatching {
            db.userProfileDao().getProfile().first()
        }.getOrNull() ?: UserProfile()
    }

    /**
     * Import and merge data from sync source
     */
    fun importAndMerge(syncData: JSONObject): ImportResult = withContext(Dispatchers.IO) {
        val imported = mutableMapOf<String, Int>()

        try {
            // Merge sessions
            val sessions = syncData.optJSONArray("sessions")
            if (sessions != null) {
                for (i in 0 until sessions.length()) {
                    val sessionMap = sessions.getJSONObject(i)
                    val session = mapToStudySession(sessionMap)
                    val id = session.id
                    if (id == 0L) {
                        db.studySessionDao().insert(session)
                    }
                    imported["sessions"] = imported.getOrDefault("sessions", 0) + 1
                }
            }

            // Update timestamps
            if (syncData.has("timestamp")) {
                syncTimestamp = syncData.getLong("timestamp")
            }

            ImportResult(true, imported)
        } catch (e: Exception) {
            ImportResult(false, imported, e.message.toString())
        }
    }

    /**
     * Import routine from sync source
     */
    fun importRoutine(routineData: JSONObject): Result<Boolean> {
        return try {
            val routine = mapToRoutine(JSONObject)
            db.routineDao().insert(routine)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        // Conflict resolution strategies
        enum class ConflictStrategy {
            NEWER_WINS,
            LOCAL_WINS,
            REMOTE_WINS
        }
    }
}

data class ImportResult(
    val success: Boolean,
    val imported: Map<String, Int> = emptyMap(),
    val errorMessage: String? = null
)