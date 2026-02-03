package com.porashona.studymaster.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(
    private val onCheck: (Task, Boolean) -> Unit,
    private val onDelete: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTitle.text = task.title
            binding.cbTask.isChecked = task.isCompleted

            if (task.dueDate != null) {
                binding.tvDate.text = SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(task.dueDate))
            }

            // Strike through if completed
            if (task.isCompleted) {
                binding.tvTitle.paintFlags = binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.root.alpha = 0.5f
            } else {
                binding.tvTitle.paintFlags = binding.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.root.alpha = 1f
            }

            binding.cbTask.setOnCheckedChangeListener { _, isChecked -> onCheck(task, isChecked) }
            binding.btnDelete.setOnClickListener { onDelete(task) }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}