package com.porashona.studymaster.ui.exams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.data.repository.ExtendedRepository
import com.porashona.studymaster.databinding.DialogAddExamBinding
import com.porashona.studymaster.databinding.FragmentExamsBinding
import com.porashona.studymaster.databinding.ItemExamBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ExamsFragment : Fragment() {
    private var _binding: FragmentExamsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExamsViewModel by viewModels {
        ExamsViewModelFactory((requireActivity().application as StudyMasterApplication).extendedRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ExamAdapter { exam -> viewModel.deleteExam(exam) }
        binding.recyclerExams.layoutManager = LinearLayoutManager(context)
        binding.recyclerExams.adapter = adapter

        binding.fabAddExam.setOnClickListener { showAddExamDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.exams.collectLatest { adapter.submitList(it) }
        }
    }

    private fun showAddExamDialog() {
        val dialogBinding = DialogAddExamBinding.inflate(layoutInflater)
        var selectedDate = System.currentTimeMillis()

        dialogBinding.btnDate.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().build()
            picker.addOnPositiveButtonClickListener {
                selectedDate = it
                dialogBinding.btnDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
            }
            picker.show(parentFragmentManager, "date")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.add_exam))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val name = dialogBinding.etName.text.toString()
                if (name.isNotEmpty()) {
                    viewModel.addExam(Exam(name = name, examDate = selectedDate))
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}

class ExamsViewModel(private val repository: ExtendedRepository) : ViewModel() {
    val exams: Flow<List<Exam>> = repository.allExams
    fun addExam(exam: Exam) = viewModelScope.launch { repository.insertExam(exam) }
    fun deleteExam(exam: Exam) = viewModelScope.launch { repository.deleteExam(exam) }
}

class ExamsViewModelFactory(private val repository: ExtendedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ExamsViewModel(repository) as T
}

class ExamAdapter(private val onDelete: (Exam) -> Unit) : ListAdapter<Exam, ExamAdapter.ViewHolder>(ExamDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExamBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(val binding: ItemExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exam: Exam) {
            binding.tvName.text = exam.name
            val daysLeft = ((exam.examDate - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).coerceAtLeast(0)
            binding.tvDate.text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(exam.examDate))
            binding.tvCountdown.text = "$daysLeft days left"
            binding.btnDelete.setOnClickListener { onDelete(exam) }
        }
    }
    class ExamDiffCallback : DiffUtil.ItemCallback<Exam>() {
        override fun areItemsTheSame(old: Exam, new: Exam) = old.id == new.id
        override fun areContentsTheSame(old: Exam, new: Exam) = old == new
    }
}