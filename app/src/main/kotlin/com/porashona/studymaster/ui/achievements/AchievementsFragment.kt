package com.porashona.studymaster.ui.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.databinding.FragmentAchievementsBinding
import kotlinx.coroutines.launch

class AchievementsFragment : Fragment() {

    private var _binding: FragmentAchievementsBinding? = null
    private val binding get() = _binding!!

    private val repo: StudyRepository by lazy {
        (requireActivity().application as StudyMasterApplication).studyRepository
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAchievementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AchievementRowAdapter()
        binding.recyclerAchievements.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerAchievements.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repo.userProfile.collect { p ->
                    if (p == null) return@collect
                    binding.tvLevel.text = getString(R.string.level_label, p.level)
                    binding.progressXp.max = p.getXpForNextLevel()
                    binding.progressXp.progress = p.getXpProgress()
                    binding.tvXp.text = getString(
                        R.string.xp_progress,
                        p.getXpProgress(),
                        p.getXpForNextLevel(),
                    )
                    binding.tvStreak.text = getString(R.string.streak_days, p.currentStreak)
                    binding.tvLongestStreak.text =
                        getString(R.string.longest_streak, p.longestStreak)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repo.allAchievements.collect { list ->
                    adapter.submitList(
                        list.sortedWith(compareByDescending<com.porashona.studymaster.data.model.Achievement> {
                            it.isUnlocked
                        }.thenByDescending { it.progress.toFloat() / it.targetProgress.coerceAtLeast(1) })
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
