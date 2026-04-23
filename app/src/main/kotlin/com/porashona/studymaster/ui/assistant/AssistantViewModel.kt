package com.porashona.studymaster.ui.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.porashona.studymaster.data.repository.ExtendedRepository
import com.porashona.studymaster.data.repository.StudyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class AssistantViewModel(
    studyRepository: StudyRepository,
    extendedRepository: ExtendedRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val suggestions: Flow<List<StudyAssistantEngine.Suggestion>> = combine(
        studyRepository.userProfile,
        studyRepository.allSubjects,
        studyRepository.allSessions,
        extendedRepository.allTasks,
        extendedRepository.allGoals
    ) { profile, subjects, sessions, tasks, goals ->
        val recent = run {
            val cutoff = System.currentTimeMillis() - 14L * 24 * 60 * 60 * 1000
            sessions.filter { it.startTime.time >= cutoff }
        }
        StudyAssistantEngine.suggest(
            StudyAssistantEngine.Input(
                profile = profile,
                subjects = subjects,
                recentSessions = recent,
                openTasks = tasks.filter { !it.isCompleted },
                activeGoals = goals
            )
        )
    }.map { it } // stabilise inferred type
}

class AssistantViewModelFactory(
    private val studyRepository: StudyRepository,
    private val extendedRepository: ExtendedRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AssistantViewModel(studyRepository, extendedRepository) as T
}
