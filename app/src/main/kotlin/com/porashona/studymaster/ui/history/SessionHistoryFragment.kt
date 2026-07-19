package com.porashona.studymaster.ui.history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.databinding.FragmentSessionHistoryBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SessionHistoryFragment : Fragment() {

    private var _binding: FragmentSessionHistoryBinding? = null
    private val binding get() = _binding!!

    private val repo: StudyRepository by lazy {
        (requireActivity().application as StudyMasterApplication).studyRepository
    }

    private enum class Filter { ALL, TODAY, WEEK, MONTH }
    private val filter = MutableStateFlow(Filter.ALL)

    private lateinit var adapter: SessionAdapter

    private val exportLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("text/csv")
    ) { uri: Uri? -> if (uri != null) doExportCsv(uri) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSessionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SessionAdapter(
            onClick = { showDetails(it) },
            onLongClick = { confirmDelete(it) },
        )
        binding.recyclerSessions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSessions.adapter = adapter

        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val id = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
            filter.value = when (id) {
                R.id.chipToday -> Filter.TODAY
                R.id.chipWeek -> Filter.WEEK
                R.id.chipMonth -> Filter.MONTH
                else -> Filter.ALL
            }
        }

        binding.btnExport.setOnClickListener {
            val stamp = SimpleDateFormat("yyyyMMdd-HHmm", Locale.US).format(Date())
            runCatching { exportLauncher.launch("sessions-$stamp.csv") }
                .onFailure {
                    Snackbar.make(binding.root, R.string.no_file_picker, Snackbar.LENGTH_LONG).show()
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repo.allSessions.combine(filter) { sessions, f -> applyFilter(sessions, f) }
                    .collect { list ->
                        adapter.submitList(list)
                        binding.tvEmpty.visibility =
                            if (list.isEmpty()) View.VISIBLE else View.GONE
                        val workList = list.filter { it.sessionType == SessionType.WORK }
                        val totalMin = workList.sumOf { it.durationInSeconds } / 60
                        binding.tvTotalMinutes.text = when {
                            totalMin >= 60 -> "${totalMin / 60}h ${totalMin % 60}m"
                            else -> "${totalMin}m"
                        }
                        binding.tvSessionCount.text =
                            getString(R.string.total_sessions_format, workList.size)
                    }
            }
        }
    }

    private fun applyFilter(sessions: List<StudySession>, f: Filter): List<StudySession> {
        if (f == Filter.ALL) return sessions
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val cutoff = when (f) {
            Filter.TODAY -> cal.timeInMillis
            Filter.WEEK -> cal.apply { add(Calendar.DAY_OF_YEAR, -6) }.timeInMillis
            Filter.MONTH -> cal.apply { add(Calendar.DAY_OF_YEAR, -29) }.timeInMillis
            Filter.ALL -> 0L
        }
        return sessions.filter { it.startTime.time >= cutoff }
    }

    private fun showDetails(s: StudySession) {
        val container = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            val pad = (resources.displayMetrics.density * 16).toInt()
            setPadding(pad, pad, pad, 0)
        }
        val layout = TextInputLayout(requireContext()).apply {
            hint = getString(R.string.session_notes_hint)
        }
        val input = TextInputEditText(layout.context).apply {
            setText(s.notes)
            minLines = 3
        }
        layout.addView(input)
        container.addView(
            layout,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ),
        )

        val mins = (s.durationInSeconds / 60).toInt()
        val fmt = SimpleDateFormat("MMM d, yyyy • HH:mm", Locale.getDefault())
        val details = buildString {
            append(s.subjectName.ifBlank { "Study" }).append('\n')
            append(fmt.format(s.startTime)).append('\n')
            append(if (mins >= 60) "${mins / 60}h ${mins % 60}m" else "${mins}m")
            if (s.xpEarned > 0) append(" • +${s.xpEarned} XP")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.session_details)
            .setMessage(details)
            .setView(container)
            .setNeutralButton(R.string.action_delete) { _, _ -> confirmDelete(s) }
            .setNegativeButton(R.string.action_close, null)
            .setPositiveButton(R.string.action_save) { _, _ ->
                val newNotes = input.text?.toString().orEmpty()
                lifecycleScope.launch { repo.updateSession(s.copy(notes = newNotes)) }
            }
            .show()
    }

    private fun confirmDelete(s: StudySession) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_session_title)
            .setMessage(R.string.delete_session_confirm)
            .setNegativeButton(R.string.action_close, null)
            .setPositiveButton(R.string.action_delete) { _, _ ->
                lifecycleScope.launch { repo.deleteSession(s) }
            }
            .show()
    }

    private fun doExportCsv(uri: Uri) {
        lifecycleScope.launch {
            runCatching {
                val sessions = adapter.currentList
                val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                val csv = buildString {
                    append("id,subject,type,start,end,minutes,xp,notes\n")
                    sessions.forEach { s ->
                        val mins = s.durationInSeconds / 60
                        val notes = s.notes.replace("\"", "\"\"").replace("\n", " ")
                        append(s.id).append(',')
                        append('"').append(s.subjectName.replace("\"", "\"\"")).append('"').append(',')
                        append(s.sessionType.name).append(',')
                        append(fmt.format(s.startTime)).append(',')
                        append(fmt.format(s.endTime)).append(',')
                        append(mins).append(',')
                        append(s.xpEarned).append(',')
                        append('"').append(notes).append('"').append('\n')
                    }
                }
                requireContext().contentResolver.openOutputStream(uri)?.use { out ->
                    out.write(csv.toByteArray(Charsets.UTF_8))
                } ?: error("Could not open output")
                Snackbar.make(
                    binding.root, getString(R.string.history_exported, sessions.size),
                    Snackbar.LENGTH_LONG,
                ).show()
            }.onFailure {
                Snackbar.make(
                    binding.root,
                    getString(R.string.backup_failed, it.message ?: it.javaClass.simpleName),
                    Snackbar.LENGTH_LONG,
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
