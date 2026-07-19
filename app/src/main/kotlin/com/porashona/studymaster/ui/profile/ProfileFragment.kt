package com.porashona.studymaster.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.databinding.DialogEditNameBinding
import com.porashona.studymaster.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(getRepository())
    }

    private lateinit var achievementAdapter: AchievementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupAchievementsRecyclerView()
        observeViewModel()
    }

    private fun getRepository(): StudyRepository =
        (requireActivity().application as StudyMasterApplication).studyRepository

    private fun setupUI() {
        binding.cardEditName.setOnClickListener {
            showEditNameDialog()
        }

        binding.cardSettings.setOnClickListener {
            showSettingsDialog()
        }

        binding.cardAbout.setOnClickListener {
            showAboutDialog()
        }
    }

    private fun setupAchievementsRecyclerView() {
        achievementAdapter = AchievementAdapter()
        binding.recyclerAchievements.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = achievementAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userProfile.collectLatest { profile ->
                profile?.let {
                    binding.tvUserName.text = it.name
                    binding.tvLevel.text = "লেভেল ${it.level}"
                    binding.tvXp.text = "${it.getXpProgress()} / ${it.getXpForNextLevel()} XP"
                    binding.progressXp.max = it.getXpForNextLevel()
                    binding.progressXp.progress = it.getXpProgress()
                    
                    // Stats
                    val totalHours = it.totalStudyTimeSeconds / 3600
                    val totalMinutes = (it.totalStudyTimeSeconds % 3600) / 60
                    binding.tvTotalTime.text = "${totalHours}ঘ ${totalMinutes}মি"
                    binding.tvTotalSessions.text = "${it.totalSessions}"
                    binding.tvCurrentStreak.text = "${it.currentStreak} দিন"
                    binding.tvLongestStreak.text = "${it.longestStreak} দিন"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.achievements.collectLatest { achievements ->
                achievementAdapter.submitList(achievements)
                
                val unlocked = achievements.count { it.isUnlocked }
                binding.tvAchievementCount.text = "$unlocked/${achievements.size}"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.levelTitle.collectLatest { title ->
                binding.tvLevelTitle.text = title
            }
        }
    }

    private fun showEditNameDialog() {
        val dialogBinding = DialogEditNameBinding.inflate(layoutInflater)
        dialogBinding.etName.setText(binding.tvUserName.text)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("নাম পরিবর্তন করুন")
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val newName = dialogBinding.etName.text.toString().trim()
                if (newName.isNotEmpty()) {
                    viewModel.updateName(newName)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showSettingsDialog() {
        val options = arrayOf(
            "🔔 বিজ্ঞপ্তি সেটিংস",
            "⏱️ টাইমার সেটিংস",
            "🎨 থিম পরিবর্তন",
            "🔊 শব্দ সেটিংস"
        )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.settings))
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showNotificationSettings()
                    1 -> showTimerSettings()
                    2 -> showThemeSettings()
                    3 -> showSoundSettings()
                }
            }
            .setNegativeButton(getString(R.string.close), null)
            .show()
    }

    private fun showNotificationSettings() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("বিজ্ঞপ্তি সেটিংস")
            .setMessage("বিজ্ঞপ্তি সেটিংস শীঘ্রই আসছে!")
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    private fun showTimerSettings() {
        val durations = arrayOf("15 মিনিট", "20 মিনিট", "25 মিনিট", "30 মিনিট", "45 মিনিট", "60 মিনিট")
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("পোমোডোরো সময়কাল")
            .setItems(durations) { _, which ->
                // Save preference (would use DataStore in production)
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("${durations[which]} সেট করা হয়েছে!")
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showThemeSettings() {
        val themes = arrayOf("🌞 লাইট মোড", "🌙 ডার্ক মোড", "📱 সিস্টেম ডিফল্ট")
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("থিম নির্বাচন করুন")
            .setItems(themes) { _, _ ->
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("থিম পরিবর্তন শীঘ্রই আসছে!")
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showSoundSettings() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("শব্দ সেটিংস")
            .setMessage("শব্দ সেটিংস শীঘ্রই আসছে!")
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    private fun showAboutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("পড়াশোনা মাস্টার")
            .setMessage("""
                সংস্করণ: 1.0.0
                
                পড়াশোনা মাস্টার হলো একটি আধুনিক স্টাডি অ্যাপ যা পোমোডোরো টেকনিক ব্যবহার করে আপনার পড়াশোনাকে আরও কার্যকর করে তোলে।
                
                বৈশিষ্ট্যসমূহ:
                • পোমোডোরো টাইমার
                • বিস্তারিত পরিসংখ্যান
                • রুটিন পরিকল্পনা
                • গেমিফিকেশন ও অর্জন
                
                তৈরি করেছে: পড়াশোনা টিম
            """.trimIndent())
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}