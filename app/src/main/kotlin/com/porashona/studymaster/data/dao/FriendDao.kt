package com.porashona.studymaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.porashona.studymaster.data.model.Friend
import com.porashona.studymaster.data.model.FriendStatus

@Dao
interface FriendDao {
    @Query("SELECT * FROM friends WHERE friendId = :friendId")
    suspend fun getFriendById(friendId: String): Friend?

    @Query("SELECT * FROM friends WHERE userId = :userId AND friendStatus = :status ORDER BY joinedAt DESC")
    suspend fun getFriendsByUser(userId: String, status: FriendStatus = FriendStatus.ACCEPTED): List<Friend>

    @Query("SELECT * FROM friends WHERE friendStatus = :status AND (userId = :userId OR friendId = :userId) ORDER BY joinedAt DESC")
    suspend fun getPendingRequests(userId: String, status: FriendStatus = FriendStatus.PENDING): List<Friend>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(friend: Friend)

    @Query("UPDATE friends SET friendStatus = :status WHERE friendId = :friendId")
    suspend fun updateFriendStatus(friendId: String, status: FriendStatus)

    @Query("DELETE FROM friends WHERE friendId = :friendId")
    suspend fun deleteFriend(friendId: String)

    @Query("SELECT * FROM friends WHERE userId = :userId OR friendId = :userId ORDER BY joinedAt DESC")
    suspend fun getAllConnections(userId: String): List<Friend>
}