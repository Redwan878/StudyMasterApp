package com.porashona.studymaster.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.R
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

class SessionAnalyticsAdapter : ListAdapter<Date, SessionAnalyticsAdapter.ViewHolder>(DateDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_session, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDate: TextView = view.findViewById(R.id.tvDate)
        private val tvMinutes: TextView = view.findViewById(R.id.tvMinutes)

        fun bind(date: Date) {
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            tvDate.text = sdf.format(date)
            tvMinutes.text = calculateMinutesSince(date)
        }

        private fun calculateMinutesSince(date: Date): String {
            val now = System.currentTimeMillis()
            val diff = now - date.time
            val minutes = (diff / (1000 * 60)).toInt()
            return if (minutes < 60) "$minutes min ago" else "${minutes / 60}h ago"
        }
    }

    class DateDiffCallback : DiffUtil.ItemCallback<Date>() {
        override fun areItemsTheSame(oldItem: Date, newItem: Date): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Date, newItem: Date): Boolean {
            return oldItem == newItem
        }
    }
}