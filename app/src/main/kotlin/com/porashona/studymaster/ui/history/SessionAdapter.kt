package com.porashona.studymaster.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.databinding.ItemSessionBinding
import java.text.SimpleDateFormat
import java.util.Locale

class SessionAdapter(
    private val onClick: (StudySession) -> Unit,
    private val onLongClick: (StudySession) -> Unit,
) : ListAdapter<StudySession, SessionAdapter.ViewHolder>(DIFF) {

    private val timeFmt = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSessionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false,
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(s: StudySession) {
            binding.tvIcon.text = when (s.sessionType) {
                SessionType.WORK -> "📚"
                SessionType.SHORT_BREAK -> "☕"
                SessionType.LONG_BREAK -> "🌿"
            }
            binding.tvSubject.text = s.subjectName.ifBlank { "Study" }
            binding.tvTime.text = timeFmt.format(s.startTime)
            val mins = (s.durationInSeconds / 60).toInt()
            val secs = (s.durationInSeconds % 60).toInt()
            binding.tvDuration.text =
                if (mins >= 60) "${mins / 60}h ${mins % 60}m" else "${mins}m ${secs}s"
            if (s.notes.isNotBlank()) {
                binding.tvNotes.text = s.notes
                binding.tvNotes.visibility = android.view.View.VISIBLE
            } else {
                binding.tvNotes.visibility = android.view.View.GONE
            }
            binding.root.setOnClickListener { onClick(s) }
            binding.root.setOnLongClickListener { onLongClick(s); true }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<StudySession>() {
            override fun areItemsTheSame(a: StudySession, b: StudySession) = a.id == b.id
            override fun areContentsTheSame(a: StudySession, b: StudySession) = a == b
        }
    }
}
