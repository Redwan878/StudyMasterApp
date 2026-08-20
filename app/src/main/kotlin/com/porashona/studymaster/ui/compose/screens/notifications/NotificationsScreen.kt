/*
package com.porashona.studymaster.ui.compose.screen.notifications

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.Notification
import com.porashona.studymaster.ui.ComposeMainActivity
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.NotificationsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// MARK: - Notification Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onDismiss: () -> Unit = {},
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val notifications by viewModel.notifications.collectAsState()
    val unreadCount by viewModel.unreadCount.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedNotification by viewModel.selectedNotification.collectAsState()
    val showMarkAllRead by remember { mutableStateOf(false) }
    val showDeleteAllConfirm by remember { mutableStateOf(false) }

    // Handle navigation from intent
    // (Would be handled in ComposeMainActivity for deep links)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = "ত useridification",
                showBackButton = true,
                onNavigateBack = { onDismiss() }
            )
        }
    ) { padding ->
        if (isLoading && notifications.isEmpty()) {
            CenteredCircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
        } else {
            NotificationList(
                notifications = notifications,
                unreadCount = unreadCount,
                onNotificationClick = { notification ->
                    viewModel.selectNotification(notification)
                },
                onMarkAsRead = { notificationId ->
                    viewModel.markAsRead(notificationId)
                },
                onDelete = { notificationId ->
                    viewModel.deleteNotification(notificationId)
                },
                onMarkAllRead = {
                    showMarkAllRead = true
                },
                onDeleteAll = {
                    showDeleteAllConfirm = true
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(vertical = 8.dp)
            )
        }
    }

    // Mark all as read confirmation dialog
    if (showMarkAllRead) {
        AlertDialog(
            onDismissRequest = { showMarkAllRead = false },
            title = { Text("সব পঠিত olarak চিহ্নিত করুন?") },
            text = { Text("আপনি কি সমস্ত বিজ্ঞপ্তি পঠিত হিসাবে চিহ্নিত করতে চান? এই קרিয়া ওঠানো যাবে না।") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.markAllAsRead()
                        showMarkAllRead = false
                    }
                ) {
                    Text("হ্যাঁ, চিহ্নিত করুন")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showMarkAllRead = false }
                ) {
                    Text("বাতিল")
                }
            }
        )
    }

    // Delete all confirmation dialog
    if (showDeleteAllConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteAllConfirm = false },
            title = { Text("সব বিজ্ঞপ্তি মুছুন?") },
            text = { Text("এই কাজটি সমস্ত বিজ্ঞপ্তি শনjali করে ফেলবে এবং এটি ফেরানো যাবে না। আপনি কি নিশ্চিত?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteAllNotifications()
                        showDeleteAllConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Error)
                ) {
                    Text("হ্যাঁ, মুছুন")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteAllConfirm = false }
                ) {
                    Text("বাতিল")
                }
            }
        )
    }

    // Notification detail bottom sheet
    if (selectedNotification != null) {
        NotificationDetailBottomSheet(
            notification = selectedNotification,
            onDismiss = { viewModel.clearSelection() },
            onDelete = { notificationId ->
                viewModel.deleteNotification(notificationId)
                viewModel.clearSelection()
            }
        )
    }
}

// MARK: - Top App Bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CenterAlignedTopAppBar(
    title: String,
    showBackButton: Boolean = true,
    onNavigateBack: () -> Unit
) {
    val isDark = MaterialTheme.isSystemDark()
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "ফিরুন",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        actions = {
            // Notification count badge would go here if needed
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

// MARK: - Notification List
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationList(
    notifications: List<Notification>,
    unreadCount: Int,
    onNotificationClick: (Notification) -> Unit,
    onMarkAsRead: (String) -> Unit,
    onDelete: (String) -> Unit,
    onMarkAllRead: () -> Unit,
    onDeleteAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Header with stats and actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "বিজ্ঞপ্তি ($unreadCount अपठित)",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (unreadCount > 0) {
                    TextButton(
                        onClick = onMarkAllRead,
                        enabled = unreadCount > 0
                    ) {
                        Text("সব পঠিত", fontSize = 14.sp)
                    }
                }
                TextButton(
                    onClick = onDeleteAll,
                    enabled = notifications.isNotEmpty()
                ) {
                    Text("সব মুছুন", fontSize = 14.sp)
                }
            }
        }

        // Divider
        Divider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp
        )

        // Notification list
        if (notifications.isEmpty()) {
            EmptyState(
                message = "কোন বিজ্ঞপ্তি নেই",
                icon = Icons.Default.NotificationsNone
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = notifications,
                    key = { it.id }
                ) { notification ->
                    NotificationItem(
                        notification = notification,
                        isUnread = !notification.isRead,
                        onClick = { onNotificationClick(notification) },
                        onMarkAsRead = { onMarkAsRead(notification.id) },
                        onDelete = { onDelete(notification.id) }
                    )
                }
            }
        }
    }
}

// MARK: - Notification Item
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationItem(
    notification: Notification,
    isUnread: Boolean,
    onClick: (Notification) -> Unit,
    onMarkAsRead: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val bgColor = if (isUnread) {
        if (isPressed) Primary.copy(alpha = 0.1f) else Primary.copy(alpha = 0.05f)
    } else {
        if (isPressed) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
        else MaterialTheme.colorScheme.surfaceVariant
    }

    NotificationCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick(notification) }
            ),
        backgroundColor = bgColor,
        onDismiss = { onDelete(notification.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon and content
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Icon based on type
                when (notification.type) {
                    NotificationType.DAILY_REMINDER -> Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    NotificationType.STREAK_ALERT -> Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = Error,
                        modifier = Modifier.size(24.dp)
                    )
                    NotificationType.EXAM_COUNTDOWN -> Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        tint = Warning,
                        modifier = Modifier.size(24.dp)
                    )
                    NotificationType.WEEKLY_REPORT -> Icon(
                        imageVector = Icons.Default.Insights,
                        contentDescription = null,
                        tint = Secondary,
                        modifier = Modifier.size(24.dp)
                    )
                    NotificationType.WEAK_SUBJECT -> Icon(
                        imageVector = Icons.Default.Weakness,
                        contentDescription = null,
                        tint = Warning,
                        modifier = Modifier.size(24.dp)
                    )
                    else -> Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isUnread) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = notification.message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Time and action buttons
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = notification.timestamp.timeAgo(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (!isUnread) {
                    IconButton(
                        onClick = { onMarkAsRead(notification.id) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "পঠিত olarak চিহ্নিত করুন",
                            tint = Success
                        )
                    }
                }

                IconButton(
                    onClick = { onDelete(notification.id) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "মুছুন",
                        tint = if (isUnread) MaterialTheme.colorScheme.onSurfaceVariant else Error
                    )
                }
            }
        }
    }
}

// MARK: - Notification Card
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    elevation: Dp = 1.dp,
    onDismiss: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    GlassmorphicCard(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        variant = GlassCardVariant.FILLED,
        tint = backgroundColor,
        borderWidth = 0.dp,
        padding = 0.dp
    ) {
        Column(content = content)
    }
}

// MARK: - Empty State
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmptyState(
    message: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// MARK: - Notification Detail Bottom Sheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationDetailBottomSheet(
    notification: Notification,
    onDismiss: () -> Unit,
    onDelete: (String) -> Unit
) {
    val state = rememberModalBottomSheetState(
        initialValue = ModalSheetValue.Hidden
    )
    LaunchedEffect(Unit) {
        state.show()
    }

    ModalBottomSheet(
        scaffoldState = state,
        gestures = ModalBottomSheetDefaults.gestures(state),
        isHalvesEnabled = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "বন্ধ করুন",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Icon
            when (notification.type) {
                NotificationType.DAILY_REMINDER -> Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(48.dp)
                )
                NotificationType.STREAK_ALERT -> Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = Error,
                    modifier = Modifier.size(48.dp)
                )
                NotificationType.EXAM_COUNTDOWN -> Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    tint = Warning,
                    modifier = Modifier.size(48.dp)
                )
                NotificationType.WEEKLY_REPORT -> Icon(
                    imageVector = Icons.Default.Insights,
                    contentDescription = null,
                    tint = Secondary,
                    modifier = Modifier.size(48.dp)
                )
                NotificationType.WEAK_SUBJECT -> Icon(
                    imageVector = Icons.Default.Weakness,
                    contentDescription = null,
                    tint = Warning,
                    modifier = Modifier.size(48.dp)
                )
                else -> Icon(
                    imageVector = Icons.Default.NotificationsActive,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timestamp
            Text(
                text = notification.timestamp.formatDateTime(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Message
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { onDismiss() }
                ) {
                    Text("বন্ধ")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { onDelete(notification.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Error)
                ) {
                    Text("মুছুন")
                }
            }
        }
    }
}

// MARK: - Extensions
private fun Long.timeAgo(): String {
    val now = System.currentTimeMillis()
    val diff = now - this
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "একদম আগে"
        minutes < 1 -> "${seconds} সেকেন্ড আগে"
        minutes < 60 -> "$minutes মিনিট আগে"
        hours < 1 -> "${minutes} মিনিট আগে"
        hours < 24 -> "$hours ঘন্টা আগে"
        days < 7 -> "$days দিন আগে"
        else -> "একটি সপ্তাহের বেশি আগে"
    }
}

private fun Long.formatDateTime(): String {
    val calendar = java.util.Calendar.getInstance().apply {
        timeInMillis = this
    }
    val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
    val minute = calendar.get(java.util.Calendar.MINUTE)
    return String.format("%02d:%02d", hour, minute)
}
*/