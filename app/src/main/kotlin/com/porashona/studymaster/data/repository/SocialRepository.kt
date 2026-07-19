package com.porashona.studymaster.data.repository

import com.porashona.studymaster.data.model.*

class SocialRepository(private val database: com.porashona.studymaster.data.AppDatabase) {

    // Friend operations
    suspend fun getFriends(userId: String): List<Friend> {
        return database.friendDao().getFriendsByUser(userId)
    }

    suspend fun getPendingFriendRequests(userId: String): List<Friend> {
        return database.friendDao().getPendingRequests(userId)
    }

    suspend fun getFriendById(friendId: String): Friend? {
        return database.friendDao().getFriendById(friendId)
    }

    suspend fun addFriend(friend: Friend) {
        database.friendDao().insertFriend(friend)
    }

    suspend fun updateFriendStatus(friendId: String, status: FriendStatus) {
        database.friendDao().updateFriendStatus(friendId, status)
    }

    suspend fun removeFriend(friendId: String) {
        database.friendDao().deleteFriend(friendId)
    }

    // Leaderboard operations
    suspend fun getLeaderboard(limit: Int = 50): List<LeaderboardEntry> {
        return database.leaderboardDao().getTopEntries(limit)
    }

    suspend fun getLeaderboardForUser(userId: String): List<LeaderboardEntry> {
        return database.leaderboardDao().getUserRankEntries(userId)
    }

    suspend fun updateUserLeaderboard(userId: String, userName: String, displayName: String, profileImageUrl: String?, xp: Int, level: Int, totalTime: Long, totalSessions: Int) {
        database.leaderboardDao().updateLeaderboardEntry(userId, userName, displayName, profileImageUrl, xp, level, totalTime, totalSessions)
    }

    // Activity share operations
    suspend fun createActivityShare(share: ActivityShare) {
        database.activityShareDao().insertShare(share)
    }

    suspend fun getPublicActivityShares(limit: Int = 20): List<ActivityShare> {
        return database.activityShareDao().getPublicShares(limit)
    }

    suspend fun getUserShares(userId: String): List<ActivityShare> {
        return database.activityShareDao().getUserShares(userId)
    }

    suspend fun deleteActivityShare(shareId: Int) {
        database.activityShareDao().deleteShare(shareId)
    }

    // Notification operations
    suspend fun getNotifications(userId: String, limit: Int = 50): List<SocialNotification> {
        return database.socialNotificationDao().getUserNotifications(userId, limit)
    }

    suspend fun getUnreadNotifications(userId: String): Int {
        return database.socialNotificationDao().getUnreadCount(userId)
    }

    suspend fun markNotificationAsRead(notificationId: Int) {
        database.socialNotificationDao().markAsRead(notificationId)
    }

    suspend fun markAllNotificationsAsRead(userId: String) {
        database.socialNotificationDao().markAllAsRead(userId)
    }

    suspend fun createNotification(notification: SocialNotification) {
        database.socialNotificationDao().insertNotification(notification)
    }

    suspend fun deleteNotification(notificationId: Int) {
        database.socialNotificationDao().deleteNotification(notificationId)
    }
}