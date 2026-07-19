package com.porashona.studymaster.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.databinding.ItemStudySessionBinding

class StudyHistoryAdapter(
    private val onSessionClick: (StudySession) -> Unit
) : ListAdapter<StudySession, StudyHistoryAdapter.StudySessionViewHolder>(StudySessionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudySessionViewHolder {
        val binding = ItemStudySessionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudySessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudySessionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudySessionViewHolder(
        private val binding: ItemStudySessionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(session: StudySession) {
            binding.apply {
                // Session date (e.g., "Jan 15, 2024")
                val dateDisplay = session.date?.let { date ->
                    val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                    val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                    val calendar = java.util.Calendar.getInstance()
                    calendar.time = date
                    val monthName = months[calendar.get(java.util.Calendar.MONTH)]
                    val dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                    val dayName = days[calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1]
                    "$monthName $dayOfMonth, $dayName"
                }
                tvSessionDate.text = dateDisplay

                // Session type (Work, Short Break, Long Break)
                tvSessionType.text = when (session.type) {
                    com.porashona.studymaster.data.model.SessionType.WORK -> "পঠন"
                    com.porashona.studymaster.data.model.SessionType.SHORT_BREAK -> "ব্রেক"
                    com.porashona.studymaster.data.model.SessionType.LONG_BREAK -> "গ্রেট ব্রেক"
                }

                // Duration (e.g., "2 ঘণ্টা 15 মিনিট")
                val totalMinutes = session.durationMinutes
                val hours = totalMinutes / 60
                val minutes = totalMinutes % 60
                val durationText = if (hours > 0) {
                    if (minutes > 0) "${hours} ঘণ্টা $minutes মিনিট" else "${hours} ঘণ্টা"
                } else {
                    "${minutes} মিনিট"
                }
                tvSessionDuration.text = durationText

                // Subject (if available)
                tvSessionSubject.text = session.subjectName.ifEmpty { "বিষয় নেই" }

                // Streak indicator (if this was part of a streak)
                tvStreakIndicator.visibility = if (session.completedStreak > 1) View.VISIBLE else View.GONE
                tvStreakIndicator.text = when {
                    session.completedStreak >= 7 -> "📚"
                    session.completedStreak >= 3 -> "🔥"
                    else -> "⭐"
                }

                // Card background based on session type
                val cardColor = when (session.type) {
                    com.porashona.studymaster.data.model.SessionType.WORK -> android.graphics.Color.parseColor("#E3F2FD")
                    com.porashona.studymaster.data.model.SessionType.SHORT_BREAK -> android.graphics.Color.parseColor("#FFF3E0")
                    com.porashona.studymaster.data.model.SessionType.LONG_BREAK -> android.graphics.Color.parseColor("#F3E5F5")
                }
                cardView.setCardBackgroundColor(cardColor)

                // Click listener
                root.setOnClickListener {
                    onSessionClick(session)
                }
            }
        }
    }

    class StudySessionDiffCallback : DiffUtil.ItemCallback<StudySession>() {
        override fun areItemsTheSame(oldItem: StudySession, newItem: StudySession): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StudySession, newItem: StudySession): Boolean {
            return oldItem == newItem
        }
    }
}