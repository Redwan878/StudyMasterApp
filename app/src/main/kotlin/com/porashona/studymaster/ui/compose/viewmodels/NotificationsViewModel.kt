package com.porashona.studymaster.ui.compose.viewmodels

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.porashona.studymaster.data.dao.AcademicEventDao
import com.porashona.studymaster.data.dao.StudySessionDao
import com.porashona.studymaster.data.dao.TaskDao
import com.porashona.studymaster.data.model.AcademicEvent
import com.porashona.studymaster.data.model.Notification
import com.porashona.studymaster.data.model.NotificationType
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.ui.ComposeMainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalPermissionsApi::class)
@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val academicEventDao: AcademicEventDao,
    private val studySessionDao: StudySessionDao,
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager,
    private val context: Context
) : ViewModel() {

    // ─── Notification State ─────────────────────────────────────────────────
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount.asStateFlow()

    private val _selectedNotification = MutableStateFlow<Notification?>(null)
    val selectedNotification: StateFlow<Notification?> = _selectedNotification.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ─── Events ───────────────────────────────────────────────────────────
    private val _events = MutableSharedFlow<NotificationEvent>(extraBufferCapacity = 16)
    val events = _events.asSharedFlow()

    // ─── Permission State ───────────────────────────────────────────────
    private val notificationPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS
    )

    init {
        loadNotifications()
        scheduleRepeatingChecks()
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // Public API
    // ══════════════════════════════════════════════════════════════════════════

    fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val notifications = mutableListOf<Notification>()

                // Add exam countdown notifications
                val examNotifications = generateExamNotifications()
                notifications.addAll(examNotifications)

                // Add streak notifications
                val streakNotifications = generateStreakNotifications()
                notifications.addAll(streakNotifications)

                // Add weekly summary notifications
                val weeklyNotifications = generateWeeklyNotifications()
                notifications.addAll(weeklyNotifications)

                # Add task deadline notifications
                val taskNotifications = generateTaskNotifications()
                notifications.addAll(taskNotifications)

                # Add study session reminders
                val sessionNotifications = generateSessionReminders()
                notifications.addAll(sessionNotifications)

                # Sort by timestamp (newest first)
                _notifications.value = notifications.sortedByDescending { it.timestamp }
                _unreadCount.value = _notifications.value.count { !it.isRead }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectNotification(notification: Notification) {
        _selectedNotification.value = notification
        if (!notification.isRead) {
            markAsRead(notification.id)
        }
    }

    fun clearSelection() {
        _selectedNotification.value = null
    }

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            val updatedList = _notifications.value.map { notification ->
                if (notification.id == notificationId) {
                    notification.copy(isRead = true)
                } else {
                    notification
                }
            }
            _notifications.value = updatedList
            _unreadCount.value = _notifications.value.count { !it.isRead }
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            val updatedList = _notifications.value.map { it.copy(isRead = true) }
            _notifications.value = updatedList
            _unreadCount.value = 0
        }
    }

    fun deleteNotification(notificationId: String) {
        viewModelScope.launch {
            _notifications.value = _notifications.value.filter { it.id != notificationId }
            _unreadCount.value = _notifications.value.count { !it.isRead }
        }
    }

    fun deleteAllNotifications() {
        viewModelScope.launch {
            _notifications.value = emptyList()
            _unreadCount.value = 0
        }
    }

    fun clearEvent() {
        // No-op for this ViewModel
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // Notification Generation
    // ══════════════════════════════════════════════════════════════════════════

    private suspend fun generateExamNotifications(): List<Notification> {
        val now = System.currentTimeMillis()
        val exams = academicEventDao.getUpcomingExams(now).first()

        return exams.map { exam ->
            val daysUntil = ((exam.date - now) / (1000 * 60 * 60 * 24)).toInt()
            val notificationType = when {
                daysUntil <= 1 -> NotificationType.EXAM_COUNTDOWN
                daysUntil <= 3 -> NotificationType.EXAM_COUNTDOWN
                daysUntil <= 7 -> NotificationType.EXAM_COUNTDOWN
                else -> return@map null // Skip if too far in future
            } ?: return@map null

            Notification(
                id = "exam_${exam.id}",
                title = "${exam.subject} পরীক্ষা আসছে",
                message = "${daysUntil} দিন পর ${exam.name} পরীক্ষা",
                timestamp = exam.date,
                type = notificationType,
                isRead = false,
                relatedId = exam.id.toString()
            )
        }.filterNotNull()
    }

    private suspend fun generateStreakNotifications(): List<Notification> {
        val streak = studySessionDao.getCurrentStreak().first() ?: 0
        return if (streak > 0 && streak % 7 == 0) {  // Weekly streak milestones
            listOf(Notification(
                id = "streak_$streak",
                title = "অদ্ভুত Streak!",
                message = "$streak দিনের অடுத்த streak পৌঁছawi congrats!",
                timestamp = System.currentTimeMillis(),
                type = NotificationType.STREAK_ALERT,
                isRead = false
            ))
        } else {
            emptyList()
        }
    }

    private suspend fun generateWeeklyNotifications(): List<Notification> {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1 = Sunday

        // Send weekly summary suggestion on Sunday evenings
        if (dayOfWeek == Calendar.SUNDAY && calendar.get(Calendar.HOUR_OF_DAY) >= 19) {
            return listOf(Notification(
                id = "weekly_suggestion_${System.currentTimeMillis()}",
                title = "সাপ্তাহিক summation সময়",
                message = "এই هفته၏ abstractionดูว่าsummary নিইস Optional? প braço এখন তৈরি করুন",
                timestamp = System.currentTimeMillis(),
                type = NotificationType.WEEKLY_REPORT,
                isRead = false
            ))
        }
        return emptyList()
    }

    private suspend fun generateTaskNotifications(): List<Notification> {
        val now = System.currentTimeMillis()
        val todayStart = calendarWithTimeAt(0, 0, 0).timeInMillis
        val todayEnd = todayStart + 24 * 60 * 60 * 1000

        val tasks = taskDao.getTasksForDateRange(todayStart, todayEnd).first()

        return tasks.filter { !it.isCompleted }.map { task ->
            val hoursLeft = ((task.dueDate ?: 0) - now) / (1000 * 60 * 60)
            val notificationType = when {
                hoursLeft <= 1 -> NotificationType.EXAM_COUNTDOWN  // Reuse for urgent tasks
                hoursLeft <= 24 -> NotificationType.EXAM_COUNTDOWN
                else -> return@map null
            } ?: return@map null

            Notification(
                id = "task_${task.id}",
                title = "งานด่วน: ${task.title}",
                message = "${hoursLeft.toInt()} ঘণ্টা বाकি '${task.description}' का काम पूरा करने के लिए",
                timestamp = task.dueDate ?: now,
                type = notificationType,
                isRead = false,
                relatedId = task.id.toString()
            )
        }.filterNotNull()
    }

    private suspend fun generateSessionReminders(): List<Notification> {
        val now = System.currentTimeMillis()
        val lastSession = studySessionDao.getLastSession().first()

        // Suggest break after long session
        val lastSessionTime = lastSession?.endTime?.time ?: 0
        val hoursSinceLast = (now - lastSessionTime) / (1000 * 60 * 60)

        return if (hoursSinceLast >= 2) {  // Suggest break after 2 hours
            listOf(Notification(
                id = "break_suggestion_${System.currentTimeMillis()}",
                title = "ব্রेक लेने का समय",
                message = "আপনি Zwe últimas 2 ঘন্টা পড়েছেন। একটি সংক্ষিপ্ত বিরতি নিন?",
                timestamp = System.currentTimeMillis(),
                type = NotificationType.WEAK_SUBJECT,  // Reuse for suggestions
                isRead = false
            ))
        } else {
            emptyList()
        }
    }

    private fun calendarWithTimeAt(hour: Int, minute: Int): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // Scheduling
    // ═════════════════════════════════════════════════════════════════════════

    private fun scheduleRepeatingChecks() {
        // Schedule periodic updates (every 30 minutes)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationUpdateReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set repeating alarm for every 30 minutes
        val thirtyMinutes = 30 * 60 * 1000L
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + thirtyMinutes,
            thirtyMinutes,
            pendingIntent
        )
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Cleanup
    // ══════════════════════════════════════════════════════════════════════════

    override fun onCleared() {
        super.onCleared()
        // Cancel scheduled alarms if needed
    }
}

// ─── Notification Receiver for Periodic Updates ───────────────────────────────
class NotificationUpdateReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: android.content.Context, intent: android.content.Intent?) {
        // Trigger notification refresh via shared preferences or other mechanism
        # In production, this would use WorkManager or similar
        val prefs = context.getSharedPreferences("notification_updates", Context.MODE_PRIVATE)
        prefs.edit().putLong("last_update", System.currentTimeMillis()).apply()
    }
}

// ─── Notification Data Class ────────────────────────────────────────────────
data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val type: NotificationType,
    val isRead: Boolean = false,
    val relatedId: String? = null
)

// ─── Notification Event ────────────────────────────────────────────────
sealed class NotificationEvent {
    data class NotificationAdded(val notification: Notification) : NotificationEvent()
    data class NotificationRemoved(val id: String) : NotificationEvent()
    data class NotificationMarkedRead(val id: String) : NotificationEvent()
    object AllNotificationsCleared : NotificationEvent()
}