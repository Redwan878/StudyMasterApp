/*
package com.porashona.studymaster.ui.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.Friend
import com.porashona.studymaster.data.model.FriendStatus
import com.porashona.studymaster.data.model.XpManager
import com.porashona.studymaster.ui.utils.GlideUtils
import com.porashona.studymaster.ui.utils.loadImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FriendsAdapter(
    private val onAccept: (Friend) -> Unit,
    private val onReject: (Friend) -> Unit,
    private val onBlock: (Friend) -> Unit,
    private val onViewProfile: (Friend) -> Unit
) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    data class FriendWithActions(
        val friend: Friend,
        val showActions: Boolean = true
    )

    private var friends = emptyList<FriendWithActions>()

    fun submitList(list: List<Friend>) {
        friends = list.map { FriendWithActions(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(friends[position])
    }

    override fun getItemCount(): Int = friends.size

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardFriend: MaterialCardView = itemView.findViewById(R.id.cardFriend)
        private val ivFriendAvatar: ImageView = itemView.findViewById(R.id.ivFriendAvatar)
        private val tvFriendName: TextView = itemView.findViewById(R.id.tvFriendName)
        private val tvFriendXp: TextView = itemView.findViewById(R.id.tvFriendXp)
        private val tvFriendLevel: TextView = itemView.findViewById(R.id.tvFriendLevel)
        private val tvFriendSince: TextView = itemView.findViewById(R.id.tvFriendSince)
        private val btnAccept: Button = itemView.findViewById(R.id.btnAccept)
        private val btnReject: Button = itemView.findViewById(R.id.btnReject)
        private val btnBlock: Button = itemView.findViewById(R.id.btnBlock)

        fun bind(friendWithActions: FriendWithActions) {
            val friend = friendWithActions.friend
            val level = XpManager.getLevel(friend.xp)

            tvFriendName.text = friend.displayName
            tvFriendXp.text = "${friend.xp} XP"
            tvFriendLevel.text = "Level $level"
            tvFriendSince.text = formatDate(friend.joinedAt)

            loadImage(ivFriendAvatar, friend.profileImageUrl ?: "")

            // Hide buttons for accepted friends
            btnAccept.visibility = if (friend.friendStatus == FriendStatus.PENDING) View.VISIBLE else View.GONE
            btnReject.visibility = if (friend.friendStatus == FriendStatus.PENDING) View.VISIBLE else View.GONE
            btnBlock.visibility = if (friend.friendStatus == FriendStatus.ACCEPTED) View.VISIBLE else View.GONE

            // Click on card to view profile
            cardFriend.setOnClickListener {
                onViewProfile(friend)
            }

            // Button listeners
            btnAccept.setOnClickListener {
                onAccept(friend)
            }

            btnReject.setOnClickListener {
                onReject(friend)
            }

            btnBlock.setOnClickListener {
                onBlock(friend)
            }
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    class FriendDiffCallback : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem.friendId == newItem.friendId
        }

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem == newItem
        }
    }
}
*/