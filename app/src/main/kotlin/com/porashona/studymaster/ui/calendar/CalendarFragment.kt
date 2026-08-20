/*
package com.porashona.studymaster.ui.calendar

import android.app.TimePickerDialog
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
import com.porashona.studymaster.data.model.AcademicEvent
import com.porashona.studymaster.data.model.EventType
import com.porashona.studymaster.databinding.DialogAddEventBinding
import com.porashona.studymaster.databinding.FragmentCalendarBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Full CRUD for academic calendar events. Previously this fragment returned
 * an empty FrameLayout as a placeholder. The underlying `AcademicEvent`
 * entity, DAO and repository methods already existed — this just wires up a
 * RecyclerView + FAB + add/edit dialog on top of them.
 */
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory(
            (requireActivity().application as StudyMasterApplication).extendedRepository
        )
    }

    private lateinit var adapter: CalendarAdapter
    private val previewFormatter = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CalendarAdapter(
            onClick = { event -> showEventDialog(event) },
            onDelete = { event -> confirmDelete(event) }
        )
        binding.recyclerEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerEvents.adapter = adapter

        binding.fabAddEvent.setOnClickListener { showEventDialog(null) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collectLatest { events ->
                adapter.submitList(events)
                binding.tvEmpty.visibility = if (events.isEmpty()) View.VISIBLE else View.GONE
                binding.recyclerEvents.visibility = if (events.isEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    private fun confirmDelete(event: AcademicEvent) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete)
            .setMessage(event.title)
            .setPositiveButton(R.string.delete) { _, _ -> viewModel.delete(event) }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showEventDialog(existing: AcademicEvent?) {
        val dialogBinding = DialogAddEventBinding.inflate(layoutInflater)

        var selectedDate: Long = existing?.date ?: System.currentTimeMillis()
        var selectedTime: String? = existing?.time
        var selectedType: EventType = existing?.eventType ?: EventType.EXAM

        fun refreshPreview() {
            val datePart = previewFormatter.format(Date(selectedDate))
            dialogBinding.tvDateTimePreview.text = if (selectedTime.isNullOrBlank()) {
                datePart
            } else {
                "$datePart · $selectedTime"
            }
        }

        existing?.let {
            dialogBinding.etTitle.setText(it.title)
            dialogBinding.etDescription.setText(it.description)
        }
        refreshPreview()

        // Chip ↔ EventType mapping.
        val typeChipMap = mapOf(
            dialogBinding.chipExam.id to EventType.EXAM,
            dialogBinding.chipAssignment.id to EventType.ASSIGNMENT_DUE,
            dialogBinding.chipClass.id to EventType.CLASS,
            dialogBinding.chipHoliday.id to EventType.HOLIDAY,
            dialogBinding.chipOther.id to EventType.OTHER
        )
        // Pre-select the right chip for existing events.
        val chipToSelect = typeChipMap.entries.firstOrNull { it.value == selectedType }?.key
        chipToSelect?.let { dialogBinding.typeChipGroup.check(it) }
        dialogBinding.typeChipGroup.setOnCheckedStateChangeListener { _, ids ->
            val id = ids.firstOrNull() ?: return@setOnCheckedStateChangeListener
            selectedType = typeChipMap[id] ?: selectedType
        }

        dialogBinding.btnPickDate.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setSelection(selectedDate)
                .build()
            picker.addOnPositiveButtonClickListener { millis ->
                selectedDate = millis
                refreshPreview()
            }
            picker.show(parentFragmentManager, "calendar_date_picker")
        }

        dialogBinding.btnPickTime.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(
                requireContext(),
                { _, hour, minute ->
                    selectedTime = "%02d:%02d".format(hour, minute)
                    refreshPreview()
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (existing == null) R.string.calendar_add_event else R.string.calendar_edit_event)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.save) { _, _ ->
                val title = dialogBinding.etTitle.text?.toString()?.trim().orEmpty()
                if (title.isEmpty()) return@setPositiveButton
                val description = dialogBinding.etDescription.text?.toString()?.trim().orEmpty()

                val event = existing?.copy(
                    title = title,
                    description = description,
                    eventType = selectedType,
                    date = selectedDate,
                    time = selectedTime,
                    isHoliday = selectedType == EventType.HOLIDAY
                ) ?: AcademicEvent(
                    title = title,
                    description = description,
                    eventType = selectedType,
                    date = selectedDate,
                    time = selectedTime,
                    isHoliday = selectedType == EventType.HOLIDAY
                )
                viewModel.save(event)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

*/