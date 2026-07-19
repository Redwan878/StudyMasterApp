package com.porashona.studymaster.ui.blocker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.data.model.BlockedApp
import com.porashona.studymaster.databinding.ItemBlockedAppBinding

class BlockedAppsAdapter(
    private val onRemove: (BlockedApp) -> Unit,
    private val onToggle: (BlockedApp, Boolean) -> Unit
) : ListAdapter<BlockedApp, BlockedAppsAdapter.ViewHolder>(BlockedAppDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBlockedAppBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemBlockedAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(app: BlockedApp) {
            binding.apply {
                tvAppName.text = app.appName
                tvBlockCount.text = "${app.blockAttempts} attempts"
                switchBlocked.isChecked = app.isBlocked

                // Load icon asynchronously if needed, or use default
                try {
                    val pm = root.context.packageManager
                    val icon = pm.getApplicationIcon(app.packageName)
                    ivAppIcon.setImageDrawable(icon)
                } catch (e: Exception) {
                    // Use default icon
                }

                switchBlocked.setOnCheckedChangeListener { _, isChecked ->
                    onToggle(app, isChecked)
                }

                btnRemove.setOnClickListener {
                    onRemove(app)
                }
            }
        }
    }

    class BlockedAppDiffCallback : DiffUtil.ItemCallback<BlockedApp>() {
        override fun areItemsTheSame(oldItem: BlockedApp, newItem: BlockedApp): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: BlockedApp, newItem: BlockedApp): Boolean {
            return oldItem == newItem
        }
    }
}