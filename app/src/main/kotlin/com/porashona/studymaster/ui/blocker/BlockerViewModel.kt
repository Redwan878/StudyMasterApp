package com.porashona.studymaster.ui.blocker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.BlockedAppDao
import com.porashona.studymaster.data.model.BlockedApp
import com.porashona.studymaster.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BlockerViewModel(
    private val blockedAppDao: BlockedAppDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val blockedApps: Flow<List<BlockedApp>> = blockedAppDao.getAllBlockedApps()
    val totalBlockAttempts: Flow<Int?> = blockedAppDao.getTotalBlockAttempts()

    val blockerEnabled: Flow<Boolean> = preferencesManager.appBlockerEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val strictMode: Flow<Boolean> = preferencesManager.strictModeEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val autoBlock: Flow<Boolean> = preferencesManager.autoBlockOnTimer
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val useRoot: Flow<Boolean> = preferencesManager.useRootBlocking
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // --- Zen Mode ---
    val zenSessionEndTime: StateFlow<Long> = preferencesManager.zenSessionEndTime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val zenLastDurationMinutes: StateFlow<Int> = preferencesManager.zenLastDurationMinutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 25)

    fun addBlockedApp(app: BlockedApp) {
        viewModelScope.launch {
            blockedAppDao.insert(app)
        }
    }

    fun removeBlockedApp(app: BlockedApp) {
        viewModelScope.launch {
            blockedAppDao.delete(app)
        }
    }

    fun toggleAppBlocked(packageName: String, isBlocked: Boolean) {
        viewModelScope.launch {
            blockedAppDao.setBlocked(packageName, isBlocked)
        }
    }

    fun setBlockerEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setAppBlockerEnabled(enabled)
        }
    }

    fun setStrictMode(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setStrictModeEnabled(enabled)
        }
    }

    fun setAutoBlock(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setAutoBlockOnTimer(enabled)
        }
    }

    fun setUseRoot(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setUseRootBlocking(enabled)
        }
    }

    fun setZenLastDurationMinutes(minutes: Int) {
        viewModelScope.launch {
            preferencesManager.setZenLastDurationMinutes(minutes)
        }
    }

}

class BlockerViewModelFactory(
    private val blockedAppDao: BlockedAppDao,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlockerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlockerViewModel(blockedAppDao, preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
