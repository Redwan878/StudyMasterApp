/*
package com.porashona.studymaster.ui.sync

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.sync.DeviceSyncManager
import com.porashona.studymaster.databinding.FragmentSyncSettingsBinding
import kotlinx.coroutines.launch

class SyncSettingsFragment : Fragment() {

    private var _binding: FragmentSyncSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SyncSettingsViewModel by viewModels {
        SyncSettingsViewModelFactory(
            (requireActivity().application as StudyMasterApplication).deviceSyncManager
        )
    }

    private val syncManager: DeviceSyncManager
        get() = (requireActivity().application as StudyMasterApplication).deviceSyncManager

    override fun onResume() {
        super.onResume()
        syncManager.enableAutoSync()
        loadInitialSyncState()
    }

    override fun onPause() {
        super.onPause()
        syncManager.disableAutoSync()
    }

    private fun loadInitialSyncState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncManager.initializeRealtimeListener(object : DeviceSyncManager.SyncStateListener {
                    override fun onSyncStarted() {
                        binding.apply {
                            progressBarSync.visibility = View.VISIBLE
                            btnSyncNow.isEnabled = false
                        }
                    }

                    override fun onSyncCompleted(result: DeviceSyncManager.SyncResult) {
                        binding.apply {
                            progressBarSync.visibility = View.GONE
                            btnSyncNow.isEnabled = true
                        }
                    }

                    override fun onSyncProgress(itemsProcessed: Int, totalItems: Int) {
                        val progress = if (totalItems > 0) itemsProcessed * 100 / totalItems else 0
                        binding.progressBarSync.progress = progress
                    }

                    override fun onSyncError(message: String) {
                        Log.e("SyncSettings", "Sync error: $message")
                    }

                    override fun onSyncDataChanged() {
                        updateSyncStatusView()
                    }
                })
            }
        }
    }

    private fun updateSyncStatusView() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncManager.getSyncStatus().collect { status ->
                    binding.apply {
                        tvSyncStatus.text = "Sync Status: ${status.statusText}"
                        tvLastSync.text = "Last Sync: ${status.lastSyncTimeFormatted}"
                        tvPendingChanges.text = "Pending Changes: ${status.pendingChanges}"
                        tvSyncSpeed.text = "Sync Speed: ${status.syncSpeedText}"
                        tvConflictCount.text = "Conflicts: ${status.conflictCount}"
                        tvStorageUsed.text = "Storage Used: ${status.storageText}"
                        switchAutoSync.isChecked = status.autoSyncEnabled
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
*/