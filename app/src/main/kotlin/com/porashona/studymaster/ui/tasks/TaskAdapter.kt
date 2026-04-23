package com.porashona.studymaster.ui.tasks

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.TaskPriority
import com.porashona.studymaster.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TaskAdapter(
    private val onCheck: (Task, Boolean) -> Unit,
    private val onDelete: (Task) -> Unit,
    private val onClick: (Task) -> Unit = {},
) : ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback()) {

    private val dateFmt = SimpleDateFormat("MMM d", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTitle.text = task.title
            if (task.description.isNotBlank()) {
                binding.tvDescription.text = task.description
                binding.tvDescription.visibility = View.VISIBLE
            } else {
                binding.tvDescription.visibility = View.GONE
            }

            binding.cbTask.setOnCheckedChangeListener(null)
            binding.cbTask.isChecked = task.isCompleted

            val ctx = binding.root.context
            val priorityColor = when (task.priority) {
                TaskPriority.URGENT -> Color.parseColor("#E53935")
                TaskPriority.HIGH -> Color.parseColor("#FB8C00")
                TaskPriority.MEDIUM -> Color.parseColor("#7E57C2")
                TaskPriority.LOW -> Color.parseColor("#43A047")
            }
            binding.priorityBar.setBackgroundColor(priorityColor)

            binding.tvPriority.text = ctx.getString(
                when (task.priority) {
                    TaskPriority.URGENT -> R.string.priority_urgent
                    TaskPriority.HIGH -> R.string.priority_high
                    TaskPriority.MEDIUM -> R.string.priority_medium
                    TaskPriority.LOW -> R.string.priority_low
                },
            )
            (binding.tvPriority.background?.mutate() as? GradientDrawable)?.setColor(
                blendWith(priorityColor, Color.WHITE, 0.85f),
            )
            binding.tvPriority.setTextColor(priorityColor)

            val subject = task.subjectName
            if (!subject.isNullOrBlank()) {
                binding.tvSubject.text = subject
                binding.tvSubject.visibility = View.VISIBLE
            } else {
                binding.tvSubject.visibility = View.GONE
            }

            val dueDate = task.dueDate
            if (dueDate != null) {
                val now = startOfDay()
                val dueDay = startOfDay(dueDate)
                val diffDays = ((dueDay - now) / (24L * 3600 * 1000)).toInt()
                val dateText = when {
                    diffDays == 0 -> ctx.getString(R.string.due_today)
                    diffDays == 1 -> ctx.getString(R.string.due_tomorrow)
                    diffDays in -1 downTo Int.MIN_VALUE -> ctx.getString(R.string.due_overdue_days, -diffDays)
                    else -> ctx.getString(R.string.due_on, dateFmt.format(Date(dueDate)))
                }
                binding.tvDate.text = dateText
                binding.tvDate.setTextColor(
                    if (diffDays < 0 && !task.isCompleted) Color.parseColor("#E53935")
                    else ctx.resources.getColor(android.R.color.darker_gray, ctx.theme),
                )
                binding.tvDate.visibility = View.VISIBLE
            } else {
                binding.tvDate.visibility = View.GONE
            }

            if (task.isCompleted) {
                binding.tvTitle.paintFlags =
                    binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.root.alpha = 0.55f
            } else {
                binding.tvTitle.paintFlags =
                    binding.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.root.alpha = 1f
            }

            binding.cbTask.setOnCheckedChangeListener { _, checked -> onCheck(task, checked) }
            binding.btnDelete.setOnClickListener { onDelete(task) }
            binding.root.setOnClickListener { onClick(task) }
        }
    }

    private fun startOfDay(time: Long = System.currentTimeMillis()): Long {
        val cal = Calendar.getInstance().apply {
            timeInMillis = time
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        return cal.timeInMillis
    }

    private fun blendWith(color: Int, other: Int, ratio: Float): Int {
        val r = (Color.red(color) * (1 - ratio) + Color.red(other) * ratio).toInt()
        val g = (Color.green(color) * (1 - ratio) + Color.green(other) * ratio).toInt()
        val b = (Color.blue(color) * (1 - ratio) + Color.blue(other) * ratio).toInt()
        return Color.rgb(r, g, b)
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}
