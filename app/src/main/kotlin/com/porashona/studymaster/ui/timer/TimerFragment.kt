package com.porashona.studymaster.ui.timer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.databinding.DialogAddSubjectBinding
import com.porashona.studymaster.databinding.FragmentTimerBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TimerViewModel by viewModels {
        TimerViewModelFactory(getRepository())
    }

    private var mediaPlayer: MediaPlayer? = null
    private var subjects: List<Subject> = emptyList()
    private var selectedSubject: Subject? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        loadSubjects()
    }

    private fun getRepository(): StudyRepository =
        (requireActivity().application as StudyMasterApplication).studyRepository

    private fun setupUI() {
        // Session type buttons
        binding.btnWork.setOnClickListener {
            viewModel.setSessionType(SessionType.WORK)
        }
        binding.btnShortBreak.setOnClickListener {
            viewModel.setSessionType(SessionType.SHORT_BREAK)
        }
        binding.btnLongBreak.setOnClickListener {
            viewModel.setSessionType(SessionType.LONG_BREAK)
        }

        // Control buttons
        binding.btnStart.setOnClickListener {
            if (viewModel.timerState.value == TimerState.IDLE) {
                viewModel.startTimer()
            }
        }
        binding.btnPause.setOnClickListener {
            when (viewModel.timerState.value) {
                TimerState.RUNNING -> viewModel.pauseTimer()
                TimerState.PAUSED -> viewModel.resumeTimer()
                else -> {}
            }
        }
        binding.btnReset.setOnClickListener {
            viewModel.resetTimer()
        }

        // Subject selection
        binding.cardSubject.setOnClickListener {
            showSubjectPicker()
        }
        binding.btnAddSubject.setOnClickListener {
            showAddSubjectDialog()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.timeLeftFormatted.collectLatest { time ->
                binding.tvTimer.text = time
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.progress.collectLatest { progress ->
                binding.circularProgress.progress = (progress * 100).toInt()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.timerState.collectLatest { state ->
                updateUIForState(state)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sessionType.collectLatest { type ->
                updateSessionTypeUI(type)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pomodoroCount.collectLatest { count ->
                binding.tvPomodoroCount.text = "🍅 $count"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.timerFinished.collectLatest { finished ->
                if (finished) {
                    onTimerFinished()
                    viewModel.onTimerFinishedHandled()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todayStudyTime.collectLatest { seconds ->
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                binding.tvTodayTime.text = String.format("আজ: %d ঘণ্টা %d মিনিট", hours, minutes)
            }
        }
    }

    private fun loadSubjects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subjects.collectLatest { subjectList ->
                subjects = subjectList
                if (subjects.isNotEmpty() && selectedSubject == null) {
                    selectedSubject = subjects.first()
                    viewModel.setSelectedSubject(selectedSubject)
                    binding.tvSelectedSubject.text = selectedSubject?.name ?: getString(R.string.select_subject)
                }
            }
        }
    }

    private fun updateUIForState(state: TimerState) {
        when (state) {
            TimerState.IDLE -> {
                binding.btnStart.visibility = View.VISIBLE
                binding.btnPause.visibility = View.GONE
                binding.btnReset.visibility = View.GONE
                binding.btnPause.text = getString(R.string.pause)
            }
            TimerState.RUNNING -> {
                binding.btnStart.visibility = View.GONE
                binding.btnPause.visibility = View.VISIBLE
                binding.btnReset.visibility = View.VISIBLE
                binding.btnPause.text = getString(R.string.pause)
            }
            TimerState.PAUSED -> {
                binding.btnStart.visibility = View.GONE
                binding.btnPause.visibility = View.VISIBLE
                binding.btnReset.visibility = View.VISIBLE
                binding.btnPause.text = getString(R.string.resume)
            }
        }
    }

    private fun updateSessionTypeUI(type: SessionType) {
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.primary)
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.surface)
        val selectedTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        val defaultTextColor = ContextCompat.getColor(requireContext(), R.color.text_primary)

        binding.btnWork.setCardBackgroundColor(if (type == SessionType.WORK) selectedColor else defaultColor)
        binding.tvWork.setTextColor(if (type == SessionType.WORK) selectedTextColor else defaultTextColor)
        
        binding.btnShortBreak.setCardBackgroundColor(if (type == SessionType.SHORT_BREAK) selectedColor else defaultColor)
        binding.tvShortBreak.setTextColor(if (type == SessionType.SHORT_BREAK) selectedTextColor else defaultTextColor)
        
        binding.btnLongBreak.setCardBackgroundColor(if (type == SessionType.LONG_BREAK) selectedColor else defaultColor)
        binding.tvLongBreak.setTextColor(if (type == SessionType.LONG_BREAK) selectedTextColor else defaultTextColor)

        // Update title based on session type
        binding.tvTitle.text = when (type) {
            SessionType.WORK -> getString(R.string.study_time)
            SessionType.SHORT_BREAK -> getString(R.string.short_break)
            SessionType.LONG_BREAK -> getString(R.string.long_break)
        }

        // Update progress bar color
        val progressColor = when (type) {
            SessionType.WORK -> R.color.timer_work
            SessionType.SHORT_BREAK -> R.color.timer_short_break
            SessionType.LONG_BREAK -> R.color.timer_long_break
        }
        binding.circularProgress.setIndicatorColor(ContextCompat.getColor(requireContext(), progressColor))
    }

    private fun showSubjectPicker() {
        if (subjects.isEmpty()) {
            showAddSubjectDialog()
            return
        }

        val subjectNames = subjects.map { it.name }.toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.select_subject))
            .setItems(subjectNames) { _, which ->
                selectedSubject = subjects[which]
                viewModel.setSelectedSubject(selectedSubject)
                binding.tvSelectedSubject.text = selectedSubject?.name
            }
            .setNeutralButton(getString(R.string.add_subject)) { _, _ ->
                showAddSubjectDialog()
            }
            .show()
    }

    private fun showAddSubjectDialog() {
        val dialogBinding = DialogAddSubjectBinding.inflate(layoutInflater)
        
        val colors = listOf("#6C63FF", "#FF6584", "#4ECDC4", "#FFD93D", "#6BCF7F", "#FF8A65", "#BA68C8")
        var selectedColor = colors.first()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.add_subject))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val name = dialogBinding.etSubjectName.text.toString().trim()
                if (name.isNotEmpty()) {
                    viewModel.addSubject(name, selectedColor)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun onTimerFinished() {
        playSound()
        vibrate()
    }

    private fun playSound() {
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.beep)
            mediaPlayer?.setOnCompletionListener { mp ->
                mp.release()
            }
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun vibrate() {
        try {
            val vibrator = ContextCompat.getSystemService(requireContext(), Vibrator::class.java)
            vibrator?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }
}