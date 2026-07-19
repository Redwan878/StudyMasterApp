package com.porashona.studymaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.porashona.studymaster.data.model.SocialNotification

@Dao
interface SocialNotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: SocialNotification)

    @Query("SELECT * FROM social_notifications WHERE userId = :userId ORDER BY createdAt DESC LIMIT :limit")
    suspend fun getUserNotifications(userId: String, limit: Int = 50): List<SocialNotification>

    @Query("SELECT COUNT(*) FROM social_notifications WHERE userId = :userId AND isRead = 0")
    suspend fun getUnreadCount(userId: String): Int

    @Query("UPDATE social_notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markAsRead(notificationId: Int)

    @Query("UPDATE social_notifications SET isRead = 1 WHERE userId = :userId")
    suspend fun markAllAsRead(userId: String)

    @Query("DELETE FROM social_notifications WHERE id = :notificationId")
    suspend fun deleteNotification(notificationId: Int)

    @Query("SELECT * FROM social_notifications WHERE userId = :userId AND isRead = 0 ORDER BY createdAt DESC LIMIT :limit")
    suspend fun getUnreadNotifications(userId: String, limit: Int = 20): List<SocialNotification>
}