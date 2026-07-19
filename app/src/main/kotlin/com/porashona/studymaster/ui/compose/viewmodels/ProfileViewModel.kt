package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.AchievementDao
import com.porashona.studymaster.data.dao.UserProfileDao
import com.porashona.studymaster.data.model.Achievement
import com.porashona.studymaster.data.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val achievementDao: AchievementDao,
) : ViewModel() {

    val userProfile: StateFlow<UserProfile?> = userProfileDao.getProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val recentAchievements: StateFlow<List<Achievement>> = achievementDao.getUnlockedAchievements()
        .map { list -> list.sortedByDescending { it.unlockedAt ?: 0L }.take(5) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating.asStateFlow()

    fun updateName(name: String) {
        viewModelScope.launch {
            _isUpdating.value = true
            try {
                userProfileDao.updateName(name)
            } finally {
                _isUpdating.value = false
            }
        }
    }
}