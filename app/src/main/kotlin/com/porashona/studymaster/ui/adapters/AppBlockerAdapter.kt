/*
package com.porashona.studymaster.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.data.model.AppInfo
import com.porashona.studymaster.databinding.ItemAppBinding

class AppBlockerAdapter(
    private val onAppSelected: (AppInfo, Boolean) -> Unit
) : ListAdapter<AppInfo, AppBlockerAdapter.AppViewHolder>(AppInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemAppBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppViewHolder(binding, onAppSelected)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppViewHolder(
        private val binding: ItemAppBinding,
        private val onAppSelected: (AppInfo, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(appInfo: AppInfo) {
            binding.apply {
                tvAppName.text = appInfo.appName
                ivAppIcon.setImageDrawable(appInfo.appIcon)

                // Handle app selection
                switchBlocked.setOnCheckedChangeListener(null) // Clear previous listener
                switchBlocked.isChecked = isSelected
                switchBlocked.setOnCheckedChangeListener { _, isChecked ->
                    onAppSelected(appInfo, isChecked)
                }

                // Card selection visual
                root.isSelected = isSelected
                root.setOnClickListener {
                    switchBlocked.isChecked = !switchBlocked.isChecked
                }
            }
        }
    }

    // For tracking selection state
    private var isSelected: Boolean = false

    fun setSelection(appInfo: AppInfo, selected: Boolean) {
        isSelected = selected
        notifyDataSetChanged()
    }

    class AppInfoDiffCallback : DiffUtil.ItemCallback<AppInfo>() {
        override fun areItemsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
            return oldItem == newItem
        }
    }
}
*/