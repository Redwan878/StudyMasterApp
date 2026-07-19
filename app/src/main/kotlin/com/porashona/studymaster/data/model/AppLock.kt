package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// ─── Enum ────────────────────────────────────────────────────────────────

enum class LockType {
    PIN,
    BIOMETRIC,
    NONE
}

// ─── AppLockConfig ──────────────────────────────────────────────────────

@Entity(tableName = "app_lock_config")
data class AppLockConfig(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val isLocked: Boolean = false,
    val lockType: String = LockType.NONE.name,
    val pinHash: String? = null,
    val lastUnlockedAt: Long? = null
)