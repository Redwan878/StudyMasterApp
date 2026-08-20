/*
package com.porashona.studymaster.ui.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.MusicTrack
import com.porashona.studymaster.databinding.ItemMusicTrackBinding

class MusicTrackAdapter(
    private val onTrackClick: (MusicTrack) -> Unit
) : ListAdapter<MusicTrack, MusicTrackAdapter.TrackViewHolder>(TrackDiffCallback()) {

    private var selectedTrackId: Int? = null

    fun setSelectedTrack(trackId: Int?) {
        val oldSelected = selectedTrackId
        selectedTrackId = trackId

        // Refresh old and new selected items
        currentList.forEachIndexed { index, track ->
            if (track.id == oldSelected || track.id == trackId) {
                notifyItemChanged(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemMusicTrackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TrackViewHolder(
        private val binding: ItemMusicTrackBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(track: MusicTrack) {
            binding.apply {
                tvTrackTitle.text = track.title
                tvTrackArtist.text = track.artist

                // Set icon based on track type
                val icon = when {
                    track.title.contains("Lo-Fi", ignoreCase = true) -> "🎵"
                    track.title.contains("Piano", ignoreCase = true) -> "🎹"
                    track.title.contains("Nature", ignoreCase = true) -> "🌿"
                    track.title.contains("Classical", ignoreCase = true) -> "🎻"
                    track.title.contains("Jazz", ignoreCase = true) -> "🎷"
                    track.title.contains("Rain", ignoreCase = true) -> "🌧️"
                    track.title.contains("Focus", ignoreCase = true) -> "🧠"
                    track.title.contains("Meditation", ignoreCase = true) -> "🧘"
                    else -> "🎶"
                }
                tvTrackIcon.text = icon

                // Highlight selected track
                val isSelected = track.id == selectedTrackId
                if (isSelected) {
                    cardTrack.setCardBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.primary_light)
                    )
                    tvTrackTitle.setTextColor(
                        ContextCompat.getColor(root.context, R.color.primary_dark)
                    )
                    ivPlaying.visibility = android.view.View.VISIBLE
                } else {
                    cardTrack.setCardBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.surface)
                    )
                    tvTrackTitle.setTextColor(
                        ContextCompat.getColor(root.context, R.color.text_primary)
                    )
                    ivPlaying.visibility = android.view.View.GONE
                }

                root.setOnClickListener {
                    onTrackClick(track)
                }
            }
        }
    }

    class TrackDiffCallback : DiffUtil.ItemCallback<MusicTrack>() {
        override fun areItemsTheSame(oldItem: MusicTrack, newItem: MusicTrack): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MusicTrack, newItem: MusicTrack): Boolean {
            return oldItem == newItem
        }
    }
}
*/