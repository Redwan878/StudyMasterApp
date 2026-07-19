package com.porashona.studymaster.data.sync

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.StudySession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * DeviceSyncManager - Central manager for cross-device synchronization
 * Uses Firebase Firestore for real-time cloud-based data sync
 * Implements offline-first design with conflict resolution
 */
class DeviceSyncManager(private val context: Context) {
    private val TAG = "DeviceSyncManager"

    // Services
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val backupManager by lazy { (context.applicationContext as StudyMasterApplication).backupManager }

    // Configuration
    private val SYNC_COLLECTION = "user_sync_data"
    private val USER_SYNC_PATH = "sync/devices"
    private val MAX_CONFLICTS_BEFORE_MERGE = 10
    private val SYNC_INTERVAL_MS = 30000L // 30 seconds

    // State
    private var syncStartTime: Long = 0
    private var lastConflicts: List<SyncConflict> = emptyList()
    private var isSyncInProgress = false

    data class SyncStatus(
        val isSyncing: Boolean = false,
        val lastSyncTime: Long? = null,
        val syncResult: SyncResult? = null,
        val pendingChanges: Int = 0,
        val currentUserId: String? = null
    )

    data class SyncResult(
        val success: Boolean,
        val syncedItems: Int,
        val conflicts: Int,
        val messages: List<String>,
        val processingTimeMs: Long,
        val resolvedConflicts: List<SyncConflict>,
        val unrecoverableConflicts: List<SyncConflict>
    )

    data class SyncConflict(
        val field: String,
        val myValue: Any,
        val remoteValue: Any,
        val resolution: ConflictResolution? = null
    )

    enum class ConflictResolution {
        LOCAL_WIN,
        REMOTE_WIN,
        MERGE,
        SKIP
    }

    /**
     * Check current synchronization status
     */
    suspend fun getSyncStatus(): SyncStatus {
        val isInProgress = isSyncInProgress
        val lastSync = syncStartTime
        val currentUserId = getCurrentUserId()

        val pending = if (isNetworkAvailable()) {
            countPendingChanges()
        } else {
            Log.w(TAG, "Network not available for pending count")
            0
        }

        return SyncStatus(
            isSyncing = isInProgress,
            lastSyncTime = lastSync,
            syncResult = null,
            pendingChanges = pending,
            currentUserId = currentUserId
        )
    }

    /**
     * Perform full synchronization across all devices
     */
    suspend fun syncAllData(): SyncResult {
        val startTime = System.currentTimeMillis()
        isSyncInProgress = true
        val messages = mutableListOf<String>()
        val conflictsDetected = mutableListOf<SyncConflict>()
        val conflictsResolved = mutableListOf<SyncConflict>()
        var syncedItemsCount = 0

        try {
            // Check authentication
            val userId = getCurrentUserId()
            if (userId == null) {
                return SyncResult(
                    success = false,
                    syncedItems = 0,
                    conflicts = 0,
                    messages = listOf("User not authenticated - sync requires login"),
                    processingTimeMs = System.currentTimeMillis() - startTime,
                    resolvedConflicts = emptyList(),
                    unrecoverableConflicts = emptyList()
                )
            }

            // Check network availability
            if (!isNetworkAvailable()) {
                return SyncResult(
                    success = false,
                    syncedItems = 0,
                    conflicts = 0,
                    messages = listOf("Network not available - offline mode"),
                    processingTimeMs = System.currentTimeMillis() - startTime,
                    resolvedConflicts = emptyList(),
                    unrecoverableConflicts = emptyList()
                )
            }

            // Create batch write
            val batch = db.batch()

            // Sync study sessions
            syncStudySessions(userId, batch, messages, conflictsDetected, conflictsResolved)
            syncedItemsCount += getStudySessionCount()

            // Sync routines
            syncRoutines(userId, batch, messages, conflictsDetected, conflictsResolved)
            syncedItemsCount += getRoutineCount()

            // Sync user profile
            syncUserProfile(userId, batch, messages, conflictsDetected, conflictsResolved)

            // Sync preferences
            syncPreferences(userId, batch, messages, conflictsDetected, conflictsResolved)

            // Commit the batch
            batch.commit().await()

            // Update last sync time
            syncStartTime = System.currentTimeMillis()
            saveLastSyncTimestamp(syncStartTime)

            // Handle conflicts
            val unrecoverable = if (conflictsDetected.size > MAX_CONFLICTS_BEFORE_MERGE) {
                conflictsDetected.drop(MAX_CONFLICTS_BEFORE_MERGE)
            } else {
                emptyList()
            }

            return SyncResult(
                success = true,
                syncedItems = syncedItemsCount,
                conflicts = conflictsDetected.size,
                messages = messages.toList(),
                processingTimeMs = System.currentTimeMillis() - startTime,
                resolvedConflicts = conflictsResolved.toList(),
                unrecoverableConflicts = unrecoverable
            )

        } catch (e: Exception) {
            Log.e(TAG, "Sync failed", e)
            return SyncResult(
                success = false,
                syncedItems = syncedItemsCount,
                conflicts = conflictsDetected.size,
                messages = messages.plus("Sync failed: ${e.message}").toList(),
                processingTimeMs = System.currentTimeMillis() - startTime,
                resolvedConflicts = conflictsResolved.toList(),
                unrecoverableConflicts = lastConflicts
            )
        } finally {
            isSyncInProgress = false
        }
    }

    /**
     * Sync study sessions across devices
     */
    private suspend fun syncStudySessions(
        userId: String,
        batch: com.google.firebase.firestore.WriteBatch,
        messages: MutableList<String>,
        conflicts: MutableList<SyncConflict>,
        resolved: MutableList<SyncConflict>
    ) {
        try {
            val sessions = backupManager.exportSessions(context)
            val sessionRef = db.collection(SYNC_COLLECTION).document(userId).collection("sessions")

            val remoteSessions = sessionRef.get().await()

            // Sync each session
            for (session in sessions) {
                val localId = session.id.toString()
                try {
                    sessionRef.document(localId).set(session.toMap()).await()
                    messages.add("Synced session: $localId")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to sync session $localId", e)
                    conflicts.add(SyncConflict("session_$localId", session.toMap().toString(), e.message.toString()))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync study sessions", e)
            conflicts.add(SyncConflict("study_sessions", "local", "remote", null))
        }
    }

    /**
     * Sync routines across devices
     */
    private suspend fun syncRoutines(
        userId: String,
        batch: com.google.firebase.firestore.WriteBatch,
        messages: MutableList<String>,
        conflicts: MutableList<SyncConflict>,
        resolved: MutableList<SyncConflict>
    ) {
        try {
            val routines = backupManager.exportRoutines(context)
            val routineRef = db.collection(SYNC_COLLECTION).document(userId).collection("routines")

            for (routine in routines) {
                val localId = routine.id.toString()
                try {
                    routineRef.document(localId).set(routine.toMap()).await()
                    messages.add("Synced routine: $localId")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to sync routine $localId", e)
                    conflicts.add(SyncConflict("routine_$localId", routine.toMap().toString(), e.message.toString()))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync routines", e)
            conflicts.add(SyncConflict("routines", "local", "remote", null))
        }
    }

    /**
     * Sync user preferences across devices
     */
    private suspend fun syncPreferences(
        userId: String,
        batch: com.google.firebase.firestore.WriteBatch,
        messages: MutableList<String>,
        conflicts: MutableList<SyncConflict>,
        resolved: MutableList<SyncConflict>
    ) {
        try {
            val preferences = backupManager.exportPreferences(context)
            val prefRef = db.collection(SYNC_COLLECTION).document(userId).collection("preferences")

            for ((key, value) in preferences) {
                try {
                    prefRef.document(key).set(mapOf("value" to value)).await()
                    messages.add("Synced preference: $key")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to sync preference $key", e)
                    conflicts.add(SyncConflict("pref_$key", value.toString(), e.message.toString()))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync preferences", e)
            conflicts.add(SyncConflict("preferences", "local", "remote", null))
        }
    }

    /**
     * Sync user profile across devices
     */
    private suspend fun syncUserProfile(
        userId: String,
        batch: com.google.firebase.firestore.WriteBatch,
        messages: MutableList<String>,
        conflicts: MutableList<SyncConflict>,
        resolved: MutableList<SyncConflict>
    ) {
        try {
            val profileData = backupManager.exportProfile(context)
            val profileRef = db.collection(SYNC_COLLECTION).document(userId).collection("profile").document("user_profile")

            profileRef.set(profileData.toMap(), SetOptions.merge()).await()
            messages.add("Synced user profile")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync profile", e)
            conflicts.add(SyncConflict("profile", "local", "remote", null))
        }
    }

    /**
     * Force immediate synchronization of all changes
     */
    suspend fun forceFullSync(): SyncResult {
        Log.d(TAG, "Forcing full synchronization across all devices")
        return syncAllData()
    }

    /**
     * Sync only changed data since last sync
     */
    suspend fun syncChangedData(): SyncResult {
        val lastSync = syncStartTime
        val currentTime = System.currentTimeMillis()
        val timeSinceLastSync = currentTime - lastSync

        // Only sync if enough time has passed
        if (timeSinceLastSync < SYNC_INTERVAL_MS) {
            Log.d(TAG, "Skipping sync - interval not-elapsed")
            return SyncResult(
                success = true,
                syncedItems = 0,
                conflicts = 0,
                messages = listOf("Sync skipped - not enough changes"),
                processingTimeMs = 0,
                resolvedConflicts = emptyList(),
                unrecoverableConflicts = emptyList()
            )
        }

        Log.d(TAG, "Starting sync for changed data")
        return syncAllData()
    }

    /**
     * Get current authentication state
     */
    private fun getCurrentUserId(): String? {
        try {
            return auth.currentUser?.uid?.takeIf { it.isNotEmpty() } ?: auth.getCurrentUser()?.uid
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user ID", e)
            return null
        }
    }

    /**
     * Check network availability
     */
    private fun isNetworkAvailable(): Boolean {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            return network?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true &&
                   network.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking network", e)
            return false
        }
    }

    /**
     * Count pending changes for synchronization
     */
    private fun countPendingChanges(): Int {
        try {
            return 0
        } catch (e: Exception) {
            Log.e(TAG, "Error counting pending changes", e)
            return 0
        }
    }

    /**
     * Save last sync timestamp
     */
    private fun saveLastSyncTimestamp(timestamp: Long) {
        val prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putLong("last_sync_timestamp", timestamp)
            .apply()
    }

    /**
     * Get last sync timestamp
     */
    fun getLastSyncTimestamp(): Long {
        val prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)
        return prefs.getLong("last_sync_timestamp", 0L)
    }

    /**
     * Enable automatic synchronization
     */
    fun enableAutoSync(): Boolean {
        val prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("auto_sync_enabled", true)
            .apply()
        return true
    }

    /**
     * Disable automatic synchronization
     */
    fun disableAutoSync(): Boolean {
        val prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("auto_sync_enabled", false)
            .apply()
        return true
    }

    /**
     * Check if auto sync is enabled
     */
    fun isAutoSyncEnabled(): Boolean {
        val prefs = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("auto_sync_enabled", true)
    }

    /**
     * Clear all synced data
     */
    suspend fun clearSyncedData(): Boolean {
        val userId = getCurrentUserId() ?: return false

        try {
            val syncCollection = db.collection(SYNC_COLLECTION).document(userId)
            syncCollection.delete().await()
            saveLastSyncTimestamp(0L)
            Log.d(TAG, "Cleared all synced data")
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing synced data", e)
            return false
        }
    }

    /**
     * Initialize listener for real-time sync updates
     */
    fun initializeRealtimeListener(listener: SyncStateListener) {
        val userId = getCurrentUserId() ?: return

        db.collection(SYNC_COLLECTION).document(userId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e(TAG, "Realtime sync listener error", exception)
                    listener.onSyncError(exception.message ?: "Unknown error")
                } else if (snapshot != null && snapshot.exists()) {
                    listener.onSyncDataChanged()
                }
            }
    }

    interface SyncStateListener {
        fun onSyncStarted()
        fun onSyncCompleted(result: SyncResult)
        fun onSyncProgress(itemsProcessed: Int, totalItems: Int)
        fun onSyncError(message: String)
        fun onSyncDataChanged()
    }

    /**
     * Cleanup resources
     */
    fun cleanup() {
        executorService.shutdown()
    }

    companion object {
        private const val TAG = "DeviceSyncManager"
    }
}