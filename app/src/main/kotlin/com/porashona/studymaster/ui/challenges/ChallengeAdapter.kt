package com.porashona.studymaster.ui.challenges

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.Challenge
import com.porashona.studymaster.data.model.ChallengeType
import com.porashona.studymaster.databinding.ItemChallengeBinding
import com.porashona.studymaster.utils.LanguageManager

class ChallengeAdapter :
    ListAdapter<Challenge, ChallengeAdapter.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChallengeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false,
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemChallengeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(c: Challenge) {
            val context = binding.root.context
            val english = LanguageManager.isEnglish(context)
            binding.tvTitle.text = if (english) c.title else c.titleBn
            binding.tvDescription.text = if (english) c.description else c.descriptionBn
            binding.tvIcon.text = iconFor(c.type)
            binding.tvReward.text = context.getString(R.string.challenge_reward, c.xpReward)

            val target = if (c.targetValue <= 0) 1 else c.targetValue
            val current = c.currentValue.coerceIn(0, target)
            binding.progress.max = target
            binding.progress.progress = current
            binding.tvProgressText.text =
                if (c.isCompleted) context.getString(R.string.challenge_completed)
                else context.getString(R.string.challenge_progress, current, target)
            binding.root.alpha = if (c.isCompleted) 1f else 0.95f
        }

        private fun iconFor(type: ChallengeType): String = when (type) {
            ChallengeType.STUDY_HOURS -> "⏰"
            ChallengeType.POMODORO_COUNT -> "🍅"
            ChallengeType.SUBJECT_COUNT -> "📚"
            ChallengeType.EARLY_START -> "🌅"
            ChallengeType.NO_BREAK -> "🧘"
            ChallengeType.STREAK -> "🔥"
            ChallengeType.CUSTOM -> "🎯"
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Challenge>() {
            override fun areItemsTheSame(a: Challenge, b: Challenge) = a.id == b.id
            override fun areContentsTheSame(a: Challenge, b: Challenge) = a == b
        }
    }
}
