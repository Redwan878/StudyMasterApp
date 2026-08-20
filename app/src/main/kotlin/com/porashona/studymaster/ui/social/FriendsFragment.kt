/*
package com.porashona.studymaster.ui.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.Friend
import com.porashona.studymaster.data.model.FriendStatus
import com.porashona.studymaster.databinding.FragmentFriendsBinding
import kotlinx.coroutines.launch

class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FriendsViewModel by viewModels()

    private lateinit var friendsAdapter: FriendsAdapter
    private lateinit var requestsAdapter: FriendsAdapter
    private var selectedTab = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()
        setupRecyclerViews()
        observeViewModel()
        loadFriends()
    }

    private fun setupTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Friends (${viewModel.friends.size})"
                1 -> tab.text = "Pending (${viewModel.requests.size})"
                2 -> tab.text = "All"
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedTab = tab?.position ?: 0
                loadFriends()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupRecyclerViews() {
        friendsAdapter = FriendsAdapter(
            onAccept = { friend -> viewModel.acceptFriend(friend) },
            onReject = { friend -> viewModel.rejectFriend(friend) },
            onBlock = { friend -> viewModel.blockFriend(friend) },
            onViewProfile = { friend -> /* TODO: Navigate to profile */ }
        )

        requestsAdapter = FriendsAdapter(
            onAccept = { friend -> viewModel.acceptFriend(friend) },
            onReject = { friend -> viewModel.rejectFriend(friend) },
            onBlock = { friend -> viewModel.blockFriend(friend) },
            onViewProfile = { friend -> /* TODO: Navigate to profile */ }
        )

        binding.friendsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = friendsAdapter
        }

        binding.requestsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = requestsAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.friends.collect { list ->
                    friendsAdapter.submitList(list.filter { it.friendStatus == FriendStatus.ACCEPTED })
                    binding.tabLayout.getTabAt(0)?.text = "Friends (${list.filter { it.friendStatus == FriendStatus.ACCEPTED }.size})"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.requests.collect { list ->
                    requestsAdapter.submitList(list.filter { it.friendStatus == FriendStatus.PENDING })
                    binding.tabLayout.getTabAt(1)?.text = "Pending (${list.filter { it.friendStatus == FriendStatus.PENDING }.size})"
                }
            }
        }
    }

    private fun loadFriends() {
        when (selectedTab) {
            0 -> friendsAdapter.submitList(viewModel.friends.value.filter { it.friendStatus == FriendStatus.ACCEPTED })
            1 -> requestsAdapter.submitList(viewModel.requests.value.filter { it.friendStatus == FriendStatus.PENDING })
            2 -> friendsAdapter.submitList(viewModel.friends.value)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
*/