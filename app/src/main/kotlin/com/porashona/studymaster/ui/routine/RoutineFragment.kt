package com.porashona.studymaster.ui.routine

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.RepeatType
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.databinding.DialogAddRoutineBinding
import com.porashona.studymaster.databinding.FragmentRoutineBinding
import com.porashona.studymaster.utils.NotificationHelper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class RoutineFragment : Fragment() {

    private var _binding: FragmentRoutineBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RoutineViewModel by viewModels {
        RoutineViewModelFactory(getRepository(), requireContext().applicationContext)
    }

    private lateinit var adapter: RoutineAdapter
    private var subjects: List<Subject> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        observeViewModel()
    }

    private fun getRepository(): StudyRepository =
        (requireActivity().application as StudyMasterApplication).studyRepository

    private fun setupRecyclerView() {
        adapter = RoutineAdapter(
            onToggle = { routine, isEnabled ->
                viewModel.toggleRoutine(routine.id, isEnabled)
            },
            onEdit = { routine ->
                showEditRoutineDialog(routine)
            },
            onDelete = { routine ->
                showDeleteConfirmation(routine)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@RoutineFragment.adapter
        }
    }

    private fun setupFab() {
        binding.fabAddRoutine.setOnClickListener {
            // Before adding a new routine, make sure we can actually fire exact
            // alarms on Android 12+ — otherwise reminders would silently be
            // downgraded to inexact and could fire hours late.
            val helper = NotificationHelper(requireContext().applicationContext)
            if (!helper.canScheduleExactAlarms()) {
                showExactAlarmDialog()
                return@setOnClickListener
            }
            showAddRoutineDialog()
        }
    }

    private fun setupDaySelectionListeners() {
        // Setup listeners for day selection chips
        dialogBinding.chipSunday.setOnCheckedChangeListener { _, isChecked ->
            // Handle Sunday selection
        }
        dialogBinding.chipMonday.setOnCheckedChangeListener { _, isChecked ->
            // Handle Monday selection
        }
        dialogBinding.chipTuesday.setOnCheckedChangeListener { _, isChecked ->
            // Handle Tuesday selection
        }
        dialogBinding.chipWednesday.setOnCheckedChangeListener { _, isChecked ->
            // Handle Wednesday selection
        }
        dialogBinding.chipThursday.setOnCheckedChangeListener { _, isChecked ->
            // Handle Thursday selection
        }
        dialogBinding.chipFriday.setOnCheckedChangeListener { _, isChecked ->
            // Handle Friday selection
        }
        dialogBinding.chipSaturday.setOnCheckedChangeListener { _, isChecked ->
            // Handle Saturday selection
        }
    }

    private fun showExactAlarmDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.exact_alarm_required)
            .setMessage(R.string.exact_alarm_description)
            .setPositiveButton(R.string.grant_permission) { _, _ ->
                runCatching {
                    startActivity(NotificationHelper.exactAlarmSettingsIntent(requireContext()))
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.routines.collectLatest { routines ->
                adapter.submitList(routines)
                binding.emptyState.visibility = if (routines.isEmpty()) View.VISIBLE else View.GONE
                binding.recyclerView.visibility = if (routines.isEmpty()) View.GONE else View.VISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subjects.collectLatest { subjectList ->
                subjects = subjectList
            }
        }
    }

    private fun showAddRoutineDialog() {
        showRoutineDialog(null)
    }

    private fun showEditRoutineDialog(routine: Routine) {
        showRoutineDialog(routine)
    }

    private fun showRoutineDialog(existingRoutine: Routine?) {
        val dialogBinding = DialogAddRoutineBinding.inflate(layoutInflater)
        var selectedHour = existingRoutine?.hour ?: 9
        var selectedMinute = existingRoutine?.minute ?: 0
        var selectedSubjectId = existingRoutine?.subjectId ?: 0L
        var selectedRepeatType = existingRoutine?.repeatType ?: RepeatType.DAILY

        // Set initial values
        existingRoutine?.let { routine ->
            dialogBinding.etRoutineTitle.setText(routine.title)
            dialogBinding.tvSelectedTime.text = String.format("%02d:%02d", routine.hour, routine.minute)
            dialogBinding.etDuration.setText(routine.durationMinutes.toString())
        } ?: run {
            dialogBinding.tvSelectedTime.text = String.format("%02d:%02d", selectedHour, selectedMinute)
            dialogBinding.etDuration.setText("25")
        }

        // Time picker
        dialogBinding.cardTimePicker.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { _, hour, minute ->
                    selectedHour = hour
                    selectedMinute = minute
                    dialogBinding.tvSelectedTime.text = String.format("%02d:%02d", hour, minute)
                },
                selectedHour,
                selectedMinute,
                true
            ).show()
        }

        // Subject selection
        dialogBinding.cardSubjectSelect.setOnClickListener {
            if (subjects.isEmpty()) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("বিষয় নেই")
                    .setMessage("প্রথমে টাইমার থেকে একটি বিষয় যোগ করুন")
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
                return@setOnClickListener
            }

            val subjectNames = subjects.map { it.name }.toTypedArray()
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.select_subject))
                .setItems(subjectNames) { _, which ->
                    selectedSubjectId = subjects[which].id
                    dialogBinding.tvSelectedSubject.text = subjects[which].name
                }
                .show()
        }

        // Add listeners for day selection chips
        setupDaySelectionListeners()

        // Setup initial day selection state
        setupDaySelectionListeners()

        // Set existing subject name
        existingRoutine?.let { routine ->
            dialogBinding.tvSelectedSubject.text = routine.subjectName.ifEmpty { "বিষয় নির্বাচন করুন" }
        }

        // Repeat type chips
        dialogBinding.chipDaily.isChecked = selectedRepeatType == RepeatType.DAILY
        dialogBinding.chipWeekly.isChecked = selectedRepeatType == RepeatType.WEEKLY
        dialogBinding.chipOnce.isChecked = selectedRepeatType == RepeatType.ONCE
        dialogBinding.chipCustom.isChecked = selectedRepeatType == RepeatType.CUSTOM

        dialogBinding.chipGroupRepeat.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedRepeatType = when {
                checkedIds.contains(R.id.chipDaily) -> RepeatType.DAILY
                checkedIds.contains(R.id.chipWeekly) -> RepeatType.WEEKLY
                checkedIds.contains(R.id.chipOnce) -> RepeatType.ONCE
                checkedIds.contains(R.id.chipCustom) -> RepeatType.CUSTOM
                else -> RepeatType.DAILY
            }
            // Show/hide day selection based on repeat type
            dialogBinding.daySelectionContainer.visibility = if (selectedRepeatType == RepeatType.CUSTOM) View.VISIBLE else View.GONE
        }

        // Day selection initialization
        fun initDaySelection() {
            when (selectedRepeatType) {
                RepeatType.DAILY -> dialogBinding.daySelectionContainer.visibility = View.GONE
                RepeatType.WEEKLY -> {
                    dialogBinding.daySelectionContainer.visibility = View.VISIBLE
                    // Default to all days for weekly
                    dialogBinding.chipSunday.isChecked = true
                    dialogBinding.chipMonday.isChecked = true
                    dialogBinding.chipTuesday.isChecked = true
                    dialogBinding.chipWednesday.isChecked = true
                    dialogBinding.chipThursday.isChecked = true
                    dialogBinding.chipFriday.isChecked = true
                    dialogBinding.chipSaturday.isChecked = true
                }
                RepeatType.CUSTOM -> {
                    dialogBinding.daySelectionContainer.visibility = View.VISIBLE
                    // Store selected days for CUSTOM type
                }
                else -> dialogBinding.daySelectionContainer.visibility = View.GONE
            }
        }

        // Show/hide day selection based on current repeat type
        initDaySelection()

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (existingRoutine == null) getString(R.string.create_routine) else getString(R.string.edit_routine))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val title = dialogBinding.etRoutineTitle.text.toString().trim()
                val duration = dialogBinding.etDuration.text.toString().toIntOrNull() ?: 25
                val subjectName = subjects.find { it.id == selectedSubjectId }?.name ?: ""

                if (title.isEmpty()) {
                    return@setPositiveButton
                }

                val routine = Routine(
                    id = existingRoutine?.id ?: 0,
                    subjectId = selectedSubjectId,
                    subjectName = subjectName,
                    title = title,
                    hour = selectedHour,
                    minute = selectedMinute,
                    durationMinutes = duration,
                    repeatType = selectedRepeatType,
                    isEnabled = existingRoutine?.isEnabled ?: true
                )

                if (existingRoutine == null) {
                    viewModel.addRoutine(routine)
                } else {
                    viewModel.updateRoutine(routine)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()

        dialog.show()
    }

    private fun showDeleteConfirmation(routine: Routine) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_routine))
            .setMessage(getString(R.string.delete_confirmation))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteRoutine(routine)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}