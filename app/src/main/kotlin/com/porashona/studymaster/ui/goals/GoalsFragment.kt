package com.porashona.studymaster.ui.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope // ADDED IMPORT
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.Goal
import com.porashona.studymaster.data.repository.ExtendedRepository
import com.porashona.studymaster.databinding.DialogAddGoalBinding
import com.porashona.studymaster.databinding.FragmentGoalsBinding
import com.porashona.studymaster.databinding.ItemGoalBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class GoalsFragment : Fragment() {
    private var _binding: FragmentGoalsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GoalsViewModel by viewModels {
        GoalsViewModelFactory((requireActivity().application as StudyMasterApplication).extendedRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GoalAdapter { goal -> viewModel.deleteGoal(goal) }
        binding.recyclerGoals.layoutManager = LinearLayoutManager(context)
        binding.recyclerGoals.adapter = adapter

        binding.fabAddGoal.setOnClickListener { showAddGoalDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.goals.collectLatest { adapter.submitList(it) }
        }
    }

    private fun showAddGoalDialog() {
        val dialogBinding = DialogAddGoalBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.add_goal))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val title = dialogBinding.etTitle.text.toString()
                val minutes = dialogBinding.etTarget.text.toString().toIntOrNull() ?: 60
                if (title.isNotEmpty()) {
                    viewModel.addGoal(Goal(title = title, targetMinutes = minutes))
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}

class GoalsViewModel(private val repository: ExtendedRepository) : ViewModel() {
    val goals: Flow<List<Goal>> = repository.allGoals

    fun addGoal(goal: Goal) = viewModelScope.launch {
        repository.insertGoal(goal)
    }

    fun deleteGoal(goal: Goal) = viewModelScope.launch {
        repository.deleteGoal(goal)
    }
}

class GoalsViewModelFactory(private val repository: ExtendedRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = GoalsViewModel(repository) as T
}

class GoalAdapter(private val onDelete: (Goal) -> Unit) : ListAdapter<Goal, GoalAdapter.ViewHolder>(GoalDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(val binding: ItemGoalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goal: Goal) {
            binding.tvTitle.text = goal.title
            binding.tvProgress.text = "${goal.currentMinutes}/${goal.targetMinutes} min"
            binding.progressBar.max = goal.targetMinutes
            binding.progressBar.progress = goal.currentMinutes
            binding.btnDelete.setOnClickListener { onDelete(goal) }
        }
    }
    class GoalDiffCallback : DiffUtil.ItemCallback<Goal>() {
        override fun areItemsTheSame(old: Goal, new: Goal) = old.id == new.id
        override fun areContentsTheSame(old: Goal, new: Goal) = old == new
    }
}