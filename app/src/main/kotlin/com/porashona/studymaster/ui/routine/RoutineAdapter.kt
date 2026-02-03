package com.porashona.studymaster.ui.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.data.model.RepeatType
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.databinding.ItemRoutineBinding

class RoutineAdapter(
    private val onToggle: (Routine, Boolean) -> Unit,
    private val onEdit: (Routine) -> Unit,
    private val onDelete: (Routine) -> Unit
) : ListAdapter<Routine, RoutineAdapter.RoutineViewHolder>(RoutineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = ItemRoutineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RoutineViewHolder(
        private val binding: ItemRoutineBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(routine: Routine) {
            binding.apply {
                tvRoutineTitle.text = routine.title
                tvRoutineTime.text = String.format("%02d:%02d", routine.hour, routine.minute)
                tvRoutineDuration.text = "${routine.durationMinutes} মিনিট"
                tvRoutineSubject.text = routine.subjectName.ifEmpty { "সাধারণ" }
                
                tvRepeatType.text = when (routine.repeatType) {
                    RepeatType.DAILY -> "প্রতিদিন"
                    RepeatType.WEEKLY -> "সাপ্তাহিক"
                    RepeatType.ONCE -> "একবার"
                    RepeatType.CUSTOM -> "কাস্টম"
                }

                switchEnabled.isChecked = routine.isEnabled
                switchEnabled.setOnCheckedChangeListener { _, isChecked ->
                    onToggle(routine, isChecked)
                }

                btnEdit.setOnClickListener { onEdit(routine) }
                btnDelete.setOnClickListener { onDelete(routine) }

                // Dim the card if disabled
                root.alpha = if (routine.isEnabled) 1f else 0.5f
            }
        }
    }

    class RoutineDiffCallback : DiffUtil.ItemCallback<Routine>() {
        override fun areItemsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem == newItem
        }
    }
}