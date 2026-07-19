package com.porashona.studymaster.ui.routine.templates

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoutineTemplateViewModel(private val repository: RoutineTemplateRepository) : ViewModel() {
    private val _templates = MutableStateFlow<List<RoutineTemplate>>(emptyList())
    val templates: StateFlow<List<RoutineTemplate>> = _templates

    private val _selectedTemplate = MutableStateFlow<RoutineTemplate?>(null)
    val selectedTemplate: StateFlow<RoutineTemplate?> = _selectedTemplate

    private val _userCreatedTemplates = MutableStateFlow<List<RoutineTemplate>>(emptyList())
    val userCreatedTemplates: StateFlow<List<RoutineTemplate>> = _userCreatedTemplates

    init {
        loadTemplates()
    }

    fun loadTemplates() {
        viewModelScope.launch {
            repository.getAllTemplates().collect { templates ->
                _templates.value = templates
            }
        }
        viewModelScope.launch {
            repository.getUserCreatedTemplates().collect { userTemplates ->
                _userCreatedTemplates.value = userTemplates
            }
        }
    }

    fun selectTemplate(templateId: String) {
        viewModelScope.launch {
            _selectedTemplate.value = repository.getTemplate(templateId)
        }
    }

    fun createTemplate(template: RoutineTemplate, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val templateId = repository.createTemplate(template)
                onSuccess(templateId)
                loadTemplates()
            } catch (e: Exception) {
                onError("Failed to create template: ${e.message}")
            }
        }
    }

    fun updateTemplate(template: RoutineTemplate, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.updateTemplate(template)
                onSuccess()
                loadTemplates()
            } catch (e: Exception) {
                onError("Failed to update template: ${e.message}")
            }
        }
    }

    fun deleteTemplate(templateId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteTemplate(templateId)
                onSuccess()
                loadTemplates()
            } catch (e: Exception) {
                onError("Failed to delete template: ${e.message}")
            }
        }
    }

    fun getTemplateById(templateId: String): RoutineTemplate? {
        return _templates.value.find { it.id == templateId }
    }

    fun getTemplatesByCategory(category: String): List<RoutineTemplate> {
        return _templates.value.filter { it.category == category }
    }

    fun convertTemplateToRoutine(templateId: String, startDate: Long, customDate: String? = null): Routine? {
        val template = getTemplateById(templateId) ?: return null
        return repository.convertTemplateToRoutine(template, startDate, customDate)
    }

    fun searchTemplates(query: String): List<RoutineTemplate> {
        val lowerCaseQuery = query.lowercase()
        return _templates.value.filter {
            template ->
            template.name.lowercase().contains(lowerCaseQuery) ||
            template.description.lowercase().contains(lowerCaseQuery) ||
            template.subjects.any { subject -> subject.lowercase().contains(lowerCaseQuery) }
        }
    }
}

interface RoutineTemplateRepository {
    suspend fun getAllTemplates(): List<RoutineTemplate>
    suspend fun getUserCreatedTemplates(): List<RoutineTemplate>
    suspend fun getTemplate(templateId: String): RoutineTemplate?
    suspend fun createTemplate(template: RoutineTemplate): String
    suspend fun updateTemplate(template: RoutineTemplate)
    suspend fun deleteTemplate(templateId: String)
    suspend fun convertTemplateToRoutine(template: RoutineTemplate, startDate: Long, customDate: String?): Routine
}

class RoutineTemplateViewModelFactory(
    private val repository: RoutineTemplateRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoutineTemplateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoutineTemplateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Repository implementation that uses the existing RoutineTemplateManager
class RoutineTemplateRepositoryImpl(
    private val templateManager: RoutineTemplateManager
) : RoutineTemplateRepository {
    override suspend fun getAllTemplates(): List<RoutineTemplate> {
        return templateManager.templates.value
    }

    override suspend fun getUserCreatedTemplates(): List<RoutineTemplate> {
        return templateManager.templates.value.filter { !it.isDefault }
    }

    override suspend fun getTemplate(templateId: String): RoutineTemplate? {
        return templateManager.getTemplate(templateId)
    }

    override suspend fun createTemplate(template: RoutineTemplate): String {
        // In a real implementation, this would save to database/storage
        return template.id
    }

    override suspend fun updateTemplate(template: RoutineTemplate) {
        // In a real implementation, this would update in database/storage
    }

    override suspend fun deleteTemplate(templateId: String) {
        // In a real implementation, this would delete from database/storage
    }

    override suspend fun convertTemplateToRoutine(template: RoutineTemplate, startDate: Long, customDate: String?): Routine {
        return templateManager.convertTemplateToRoutine(template, startDate, customDate)
    }
}