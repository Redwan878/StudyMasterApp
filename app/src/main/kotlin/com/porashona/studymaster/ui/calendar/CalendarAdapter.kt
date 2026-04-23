package com.porashona.studymaster.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.data.model.AcademicEvent
import com.porashona.studymaster.databinding.ItemCalendarEventBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class CalendarAdapter(
    private val onClick: (AcademicEvent) -> Unit,
    private val onDelete: (AcademicEvent) -> Unit
) : ListAdapter<AcademicEvent, CalendarAdapter.Holder>(DIFF) {

    private val dateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemCalendarEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(private val binding: ItemCalendarEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: AcademicEvent) {
            binding.tvEventTitle.text = event.title
            val dateLabel = dateFormat.format(Date(event.date))
            binding.tvEventDate.text = if (event.time.isNullOrBlank()) {
                dateLabel
            } else {
                "$dateLabel · ${event.time}"
            }
            binding.tvEventType.text = event.eventType.name
            binding.tvCountdown.text = countdownLabel(event.date)

            runCatching { Color.parseColor(event.color) }
                .onSuccess { binding.colorStrip.setBackgroundColor(it) }

            binding.root.setOnClickListener { onClick(event) }
            binding.btnDelete.setOnClickListener { onDelete(event) }
        }

        private fun countdownLabel(eventMs: Long): String {
            val delta = eventMs - System.currentTimeMillis()
            if (delta <= 0L) return "সময় শেষ"
            val days = TimeUnit.MILLISECONDS.toDays(delta)
            val hours = TimeUnit.MILLISECONDS.toHours(delta) - TimeUnit.DAYS.toHours(days)
            return when {
                days > 0 -> "$days দিন $hours ঘণ্টা বাকি"
                hours > 0 -> "$hours ঘণ্টা বাকি"
                else -> "১ ঘণ্টারও কম"
            }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<AcademicEvent>() {
            override fun areItemsTheSame(old: AcademicEvent, new: AcademicEvent) = old.id == new.id
            override fun areContentsTheSame(old: AcademicEvent, new: AcademicEvent) = old == new
        }
    }
}
