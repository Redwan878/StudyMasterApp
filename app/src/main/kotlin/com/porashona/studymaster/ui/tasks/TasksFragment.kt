package com.porashona.studymaster.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.TaskPriority
import com.porashona.studymaster.data.repository.ExtendedRepository
import com.porashona.studymaster.databinding.DialogAddTaskBinding
import com.porashona.studymaster.databinding.FragmentTasksBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TasksViewModel by viewModels {
        TasksViewModelFactory(
            (requireActivity().application as StudyMasterApplication).extendedRepository
        )
    }

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupUI()
        observeData()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            onCheck = { task, checked -> viewModel.toggleTask(task, checked) },
            onDelete = { task -> viewModel.deleteTask(task) }
        )
        binding.recyclerTasks.layoutManager = LinearLayoutManager(context)
        binding.recyclerTasks.adapter = adapter
    }

    private fun setupUI() {
        binding.fabAddTask.setOnClickListener { showAddTaskDialog() }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tasks.collectLatest { tasks ->
                adapter.submitList(tasks)
                binding.emptyState.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        var selectedDate = System.currentTimeMillis()

        dialogBinding.btnDate.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().build()
            picker.addOnPositiveButtonClickListener { selectedDate = it }
            picker.show(parentFragmentManager, "date_picker")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.add_task))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val title = dialogBinding.etTitle.text.toString()
                if (title.isNotEmpty()) {
                    val task = Task(
                        title = title,
                        priority = if(dialogBinding.chipHigh.isChecked) TaskPriority.HIGH else TaskPriority.MEDIUM,
                        dueDate = selectedDate
                    )
                    viewModel.addTask(task)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}