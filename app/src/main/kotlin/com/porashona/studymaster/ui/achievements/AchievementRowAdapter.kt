package com.porashona.studymaster.ui.achievements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.Achievement
import com.porashona.studymaster.data.model.AchievementTypes
import com.porashona.studymaster.databinding.ItemAchievementRowBinding

class AchievementRowAdapter :
    ListAdapter<Achievement, AchievementRowAdapter.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAchievementRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false,
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemAchievementRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(a: Achievement) {
            val context = binding.root.context
            binding.tvIcon.text = emojiFor(a.id)
            binding.tvTitle.text = a.title
            binding.tvDescription.text = a.description
            binding.tvReward.text = "+${a.xpReward} XP"

            val target = if (a.targetProgress <= 0) 1 else a.targetProgress
            val current = a.progress.coerceIn(0, target)
            binding.progress.max = target
            binding.progress.progress = current
            binding.tvProgressText.text = if (a.isUnlocked) {
                context.getString(R.string.achievement_unlocked_status)
            } else {
                "$current / $target"
            }

            binding.root.alpha = if (a.isUnlocked) 1f else 0.55f
        }

        private fun emojiFor(id: String): String = when (id) {
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

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Achievement>() {
            override fun areItemsTheSame(a: Achievement, b: Achievement) = a.id == b.id
            override fun areContentsTheSame(a: Achievement, b: Achievement) = a == b
        }
    }
}
