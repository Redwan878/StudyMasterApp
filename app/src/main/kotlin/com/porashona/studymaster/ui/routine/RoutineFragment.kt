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

    private fun getRepository(): StudyRepository {
        val database = (requireActivity().application as StudyMasterApplication).database
        return StudyRepository(
            database.studySessionDao(),
            database.subjectDao(),
            database.routineDao(),
            database.achievementDao(),
            database.userProfileDao()
        )
    }

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
            showAddRoutineDialog()
        }
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

        // Set existing subject name
        existingRoutine?.let { routine ->
            dialogBinding.tvSelectedSubject.text = routine.subjectName.ifEmpty { "বিষয় নির্বাচন করুন" }
        }

        // Repeat type chips
        dialogBinding.chipDaily.isChecked = selectedRepeatType == RepeatType.DAILY
        dialogBinding.chipWeekly.isChecked = selectedRepeatType == RepeatType.WEEKLY
        dialogBinding.chipOnce.isChecked = selectedRepeatType == RepeatType.ONCE

        dialogBinding.chipGroupRepeat.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedRepeatType = when {
                checkedIds.contains(R.id.chipDaily) -> RepeatType.DAILY
                checkedIds.contains(R.id.chipWeekly) -> RepeatType.WEEKLY
                checkedIds.contains(R.id.chipOnce) -> RepeatType.ONCE
                else -> RepeatType.DAILY
            }
        }

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