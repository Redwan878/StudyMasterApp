package com.porashona.studymaster.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.RecurringType
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.TaskPriority
import com.porashona.studymaster.databinding.DialogAddTaskBinding
import com.porashona.studymaster.databinding.FragmentTasksBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TasksViewModel by viewModels {
        TasksViewModelFactory(
            (requireActivity().application as StudyMasterApplication).extendedRepository,
        )
    }

    private lateinit var adapter: TaskAdapter

    private enum class Filter { ALL, TODAY, OVERDUE, DONE }
    private val filter = MutableStateFlow(Filter.ALL)
    private val query = MutableStateFlow("")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TaskAdapter(
            onCheck = { task, checked -> viewModel.toggleTask(task, checked) },
            onDelete = { task -> viewModel.deleteTask(task) },
            onClick = { showAddTaskDialog(it) },
        )
        binding.recyclerTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTasks.adapter = adapter

        binding.fabAddTask.setOnClickListener { showAddTaskDialog() }

        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val id = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
            filter.value = when (id) {
                R.id.chipToday -> Filter.TODAY
                R.id.chipOverdue -> Filter.OVERDUE
                R.id.chipDone -> Filter.DONE
                else -> Filter.ALL
            }
        }

        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                query.value = s?.toString().orEmpty().trim()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(viewModel.tasks, filter, query) { tasks, f, q -> apply(tasks, f, q) }
                    .collect { list ->
                        adapter.submitList(list)
                        binding.emptyState.visibility =
                            if (list.isEmpty()) View.VISIBLE else View.GONE
                    }
            }
        }
    }

    private fun apply(tasks: List<Task>, f: Filter, q: String): List<Task> {
        val now = startOfDay()
        val tomorrow = now + 24L * 3600 * 1000
        val filtered = tasks.filter { t ->
            val matchesQuery = q.isEmpty() ||
                t.title.contains(q, ignoreCase = true) ||
                t.description.contains(q, ignoreCase = true) ||
                (t.subjectName?.contains(q, ignoreCase = true) == true)
            matchesQuery && when (f) {
                Filter.ALL -> !t.isCompleted
                Filter.TODAY -> !t.isCompleted && (t.dueDate != null &&
                    t.dueDate in now until tomorrow)
                Filter.OVERDUE -> !t.isCompleted && (t.dueDate != null && t.dueDate < now)
                Filter.DONE -> t.isCompleted
            }
        }
        return filtered.sortedWith(
            compareBy(
                { it.isCompleted },
                { it.dueDate ?: Long.MAX_VALUE },
                { -priorityRank(it.priority) },
            ),
        )
    }

    private fun priorityRank(p: TaskPriority) = when (p) {
        TaskPriority.URGENT -> 4
        TaskPriority.HIGH -> 3
        TaskPriority.MEDIUM -> 2
        TaskPriority.LOW -> 1
    }

    private fun startOfDay(time: Long = System.currentTimeMillis()): Long {
        val cal = Calendar.getInstance().apply {
            timeInMillis = time
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        return cal.timeInMillis
    }

    private fun showAddTaskDialog(existing: Task? = null) {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        var selectedDate: Long? = existing?.dueDate
        var selectedSubjectName: String? = existing?.subjectName
        var selectedSubjectId: Long? = existing?.subjectId

        existing?.let { task ->
            dialogBinding.etTitle.setText(task.title)
            dialogBinding.etDescription.setText(task.description)
            dialogBinding.chipLow.isChecked = task.priority == TaskPriority.LOW
            dialogBinding.chipMedium.isChecked = task.priority == TaskPriority.MEDIUM
            dialogBinding.chipHigh.isChecked = task.priority == TaskPriority.HIGH
            dialogBinding.chipUrgent.isChecked = task.priority == TaskPriority.URGENT
            dialogBinding.chipRecNone.isChecked = task.recurringType == RecurringType.NONE
            dialogBinding.chipRecDaily.isChecked = task.recurringType == RecurringType.DAILY
            dialogBinding.chipRecWeekly.isChecked = task.recurringType == RecurringType.WEEKLY
            dialogBinding.chipRecMonthly.isChecked = task.recurringType == RecurringType.MONTHLY
        }

        dialogBinding.btnDate.text = selectedDate?.let { formatDateShort(it) }
            ?: getString(R.string.pick_due_date)
        dialogBinding.btnDate.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setSelection(selectedDate ?: MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            picker.addOnPositiveButtonClickListener { millis ->
                // MaterialDatePicker returns UTC-midnight. Normalise to the
                // user's local midnight so due-date comparisons (which all use
                // local-tz Calendar) don't slip a day for negative UTC offsets.
                val localMidnight = millis + java.util.TimeZone.getDefault().getOffset(millis)
                selectedDate = localMidnight
                dialogBinding.btnDate.text = formatDateShort(localMidnight)
            }
            picker.show(parentFragmentManager, "task_date_picker")
        }

        dialogBinding.btnSubject.text = selectedSubjectName ?: getString(R.string.pick_subject)
        dialogBinding.btnSubject.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val studyRepo = (requireActivity().application as StudyMasterApplication).studyRepository
                val subjects = studyRepo.allSubjects.first()
                if (subjects.isEmpty()) {
                    com.google.android.material.snackbar.Snackbar.make(
                        binding.root, R.string.no_subjects_yet,
                        com.google.android.material.snackbar.Snackbar.LENGTH_SHORT,
                    ).show()
                    return@launch
                }
                val names = subjects.map { it.name }.toTypedArray()
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.pick_subject)
                    .setItems(names) { _, which ->
                        selectedSubjectId = subjects[which].id
                        selectedSubjectName = subjects[which].name
                        dialogBinding.btnSubject.text = subjects[which].name
                    }
                    .show()
            }
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (existing == null) R.string.add_task else R.string.edit_task)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.save) { _, _ ->
                val title = dialogBinding.etTitle.text?.toString()?.trim().orEmpty()
                if (title.isEmpty()) return@setPositiveButton
                val priority = when {
                    dialogBinding.chipUrgent.isChecked -> TaskPriority.URGENT
                    dialogBinding.chipHigh.isChecked -> TaskPriority.HIGH
                    dialogBinding.chipLow.isChecked -> TaskPriority.LOW
                    else -> TaskPriority.MEDIUM
                }
                val recurring = when {
                    dialogBinding.chipRecDaily.isChecked -> RecurringType.DAILY
                    dialogBinding.chipRecWeekly.isChecked -> RecurringType.WEEKLY
                    dialogBinding.chipRecMonthly.isChecked -> RecurringType.MONTHLY
                    else -> RecurringType.NONE
                }
                val xpReward = when (priority) {
                    TaskPriority.URGENT -> 40
                    TaskPriority.HIGH -> 25
                    TaskPriority.MEDIUM -> 15
                    TaskPriority.LOW -> 10
                }
                val newTask = (existing ?: Task(title = title)).copy(
                    title = title,
                    description = dialogBinding.etDescription.text?.toString()?.trim().orEmpty(),
                    priority = priority,
                    dueDate = selectedDate,
                    subjectId = selectedSubjectId,
                    subjectName = selectedSubjectName,
                    recurringType = recurring,
                    isRecurring = recurring != RecurringType.NONE,
                    xpReward = xpReward,
                )
                if (existing == null) viewModel.addTask(newTask) else viewModel.updateTask(newTask)
            }
            .setNegativeButton(R.string.cancel, null)
            .apply {
                if (existing != null) setNeutralButton(R.string.action_delete) { _, _ ->
                    viewModel.deleteTask(existing)
                }
            }
            .show()
    }

    private fun formatDateShort(ms: Long): String {
        val fmt = java.text.SimpleDateFormat("MMM d, yyyy", java.util.Locale.getDefault())
        return fmt.format(java.util.Date(ms))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
