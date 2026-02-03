package com.porashona.studymaster.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.Achievement
import com.porashona.studymaster.data.model.UserProfile
import com.porashona.studymaster.data.repository.StudyRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: StudyRepository) : ViewModel() {

    val userProfile: StateFlow<UserProfile?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val achievements: StateFlow<List<Achievement>> = repository.allAchievements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val levelTitle: StateFlow<String> = userProfile.map { profile ->
        when (profile?.level ?: 1) {
            in 1..4 -> "🌱 শিক্ষানবিস"
            in 5..9 -> "📚 নিয়মিত পাঠক"
            in 10..19 -> "⭐ মেধাবী শিক্ষার্থী"
            in 20..29 -> "🏆 বিশেষজ্ঞ"
            in 30..49 -> "👑 মাস্টার"
            else -> "🎓 গ্র্যান্ডমাস্টার"
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "🌱 শিক্ষানবিস")

    fun updateName(name: String) {
        viewModelScope.launch {
            repository.updateProfileName(name)
        }
    }
}

class ProfileViewModelFactory(private val repository: StudyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}