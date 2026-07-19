package com.porashona.studymaster.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.Achievement
import com.porashona.studymaster.data.model.AchievementTypes
import com.porashona.studymaster.databinding.ItemAchievementBinding

class AchievementAdapter : ListAdapter<Achievement, AchievementAdapter.AchievementViewHolder>(AchievementDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding = ItemAchievementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AchievementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AchievementViewHolder(
        private val binding: ItemAchievementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(achievement: Achievement) {
            binding.apply {
                // Set emoji based on achievement type
                tvAchievementIcon.text = getAchievementEmoji(achievement.id)
                
                // Set locked/unlocked state
                if (achievement.isUnlocked) {
                    cardAchievement.setCardBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.accent_light)
                    )
                    cardAchievement.alpha = 1f
                    tvAchievementIcon.alpha = 1f
                } else {
                    cardAchievement.setCardBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.surface)
                    )
                    cardAchievement.alpha = 0.5f
                    tvAchievementIcon.alpha = 0.3f
                }

                // Click to show details
                root.setOnClickListener {
                    showAchievementDetails(achievement)
                }
            }
        }

        private fun getAchievementEmoji(id: String): String {
            return when (id) {
                AchievementTypes.STREAK_7 -> "🔥"
                AchievementTypes.STREAK_30 -> "💪"
                AchievementTypes.HOURS_10 -> "⏰"
                AchievementTypes.HOURS_100 -> "📚"
                AchievementTypes.HOURS_500 -> "🎓"
                AchievementTypes.SESSIONS_10 -> "✅"
                AchievementTypes.SESSIONS_100 -> "🏅"
                AchievementTypes.PERFECT_WEEK -> "🌟"
                AchievementTypes.EARLY_BIRD -> "🌅"
                AchievementTypes.NIGHT_OWL -> "🦉"
                else -> "🏆"
            }
        }

        private fun showAchievementDetails(achievement: Achievement) {
            val context = binding.root.context
            val status = if (achievement.isUnlocked) "✅ অর্জিত!" else "🔒 অর্জিত হয়নি"
            val progress = "${achievement.progress}/${achievement.targetProgress}"
            
            android.app.AlertDialog.Builder(context)
                .setTitle(achievement.title)
                .setMessage("""
                    ${achievement.description}
                    
                    অবস্থা: $status
                    অগ্রগতি: $progress
                    পুরস্কার: ${achievement.xpReward} XP
                """.trimIndent())
                .setPositiveButton("ঠিক আছে", null)
                .show()
        }
    }

    class AchievementDiffCallback : DiffUtil.ItemCallback<Achievement>() {
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem == newItem
        }
    }
}