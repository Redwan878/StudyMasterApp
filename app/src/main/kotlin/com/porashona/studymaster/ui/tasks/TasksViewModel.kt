package com.porashona.studymaster.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.repository.ExtendedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: ExtendedRepository) : ViewModel() {
    val tasks: Flow<List<Task>> = repository.allTasks

    fun addTask(task: Task) = viewModelScope.launch { repository.insertTask(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { repository.deleteTask(task) }
    fun toggleTask(task: Task, isCompleted: Boolean) = viewModelScope.launch {
        if(isCompleted) repository.completeTask(task.id) else repository.uncompleteTask(task.id)
    }
}

class TasksViewModelFactory(private val repository: ExtendedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TasksViewModel(repository) as T
}