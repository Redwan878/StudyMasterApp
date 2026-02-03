package com.porashona.studymaster.ui.blocker

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.BlockedApp
import com.porashona.studymaster.databinding.FragmentBlockerBinding
import com.porashona.studymaster.databinding.ItemBlockedAppBinding
import com.porashona.studymaster.service.AppBlockerService
import com.porashona.studymaster.utils.RootUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BlockerFragment : Fragment() {

    private var _binding: FragmentBlockerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BlockerViewModel by viewModels {
        BlockerViewModelFactory(
            (requireActivity().application as StudyMasterApplication).database.blockedAppDao(),
            (requireActivity().application as StudyMasterApplication).preferencesManager
        )
    }

    private lateinit var blockedAppsAdapter: BlockedAppsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecyclerViews()
        observeViewModel()
        checkPermissions()
    }

    private fun setupUI() {
        binding.switchBlockerEnabled.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setBlockerEnabled(isChecked)
            if (isChecked) checkAndRequestPermissions()
        }

        binding.switchStrictMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setStrictMode(isChecked)
        }

        binding.switchAutoBlock.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAutoBlock(isChecked)
        }

        binding.switchUseRoot.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) checkRootAccess() else viewModel.setUseRoot(false)
        }

        binding.btnAddApps.setOnClickListener { showAppSelectionDialog() }
        binding.btnEnableAccessibility.setOnClickListener { openAccessibilitySettings() }
        binding.btnStartBlocking.setOnClickListener { startBlocking() }
        binding.btnStopBlocking.setOnClickListener { stopBlocking() }
    }

    private fun setupRecyclerViews() {
        blockedAppsAdapter = BlockedAppsAdapter(
            onRemove = { app -> viewModel.removeBlockedApp(app) },
            onToggle = { app, isBlocked -> viewModel.toggleAppBlocked(app.packageName, isBlocked) }
        )
        binding.recyclerBlockedApps.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = blockedAppsAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.blockerEnabled.collectLatest { enabled ->
                binding.switchBlockerEnabled.isChecked = enabled
                updateBlockingUI(enabled)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.strictMode.collectLatest { enabled -> binding.switchStrictMode.isChecked = enabled }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.autoBlock.collectLatest { enabled -> binding.switchAutoBlock.isChecked = enabled }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.useRoot.collectLatest { enabled -> binding.switchUseRoot.isChecked = enabled }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.blockedApps.collectLatest { apps ->
                blockedAppsAdapter.submitList(apps)
                binding.tvBlockedCount.text = "${apps.size} ${getString(R.string.blocked_apps)}"
                binding.emptyState.visibility = if (apps.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalBlockAttempts.collectLatest { count ->
                binding.tvBlockAttempts.text = "${count ?: 0}"
            }
        }
    }

    private fun updateBlockingUI(enabled: Boolean) {
        binding.cardBlockingStatus.setCardBackgroundColor(
            if (enabled) requireContext().getColor(R.color.success)
            else requireContext().getColor(R.color.error)
        )
        binding.tvBlockingStatus.text = if (enabled) getString(R.string.blocker_enabled) else getString(R.string.blocker_disabled)
        binding.ivBlockingStatus.setImageResource(
            if (enabled) R.drawable.ic_blocker else R.drawable.ic_blocker_off
        )
    }

    private fun checkPermissions() {
        val isAccessibilityEnabled = isAccessibilityServiceEnabled()
        binding.cardAccessibility.visibility = if (isAccessibilityEnabled) View.GONE else View.VISIBLE
    }

    private fun checkAndRequestPermissions() {
        if (!isAccessibilityServiceEnabled()) showAccessibilityDialog()
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        return enabledServices.any { it.resolveInfo.serviceInfo.packageName == requireContext().packageName }
    }

    private fun showAccessibilityDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.accessibility_required)
            .setMessage(R.string.accessibility_service_description)
            .setPositiveButton(R.string.enable_accessibility) { _, _ -> openAccessibilitySettings() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun openAccessibilitySettings() {
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }

    private fun checkRootAccess() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (RootUtils.isRootAvailable()) {
                viewModel.setUseRoot(true)
                Snackbar.make(binding.root, R.string.root_granted, Snackbar.LENGTH_SHORT).show()
            } else {
                binding.switchUseRoot.isChecked = false
                Snackbar.make(binding.root, R.string.root_not_available, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun showAppSelectionDialog() {
        val installedApps = getInstalledApps()
        val blockedPackages = blockedAppsAdapter.currentList.map { it.packageName }.toSet()
        val filteredApps = installedApps.filter { !blockedPackages.contains(it.packageName) }

        val appNames = filteredApps.map { it.appName }.toTypedArray()
        val checkedItems = BooleanArray(appNames.size)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_apps_to_block)
            .setMultiChoiceItems(appNames, checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }
            .setPositiveButton(R.string.save) { _, _ ->
                filteredApps.forEachIndexed { index, app ->
                    if (checkedItems[index]) {
                        viewModel.addBlockedApp(BlockedApp(packageName = app.packageName, appName = app.appName))
                    }
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun getInstalledApps(): List<AppInfo> {
        val pm = requireContext().packageManager
        return pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { pm.getLaunchIntentForPackage(it.packageName) != null && (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
            .map { AppInfo(it.packageName, pm.getApplicationLabel(it).toString()) }
            .sortedBy { it.appName }
    }

    private fun startBlocking() {
        viewLifecycleOwner.lifecycleScope.launch {
            val blockedApps = viewModel.blockedApps.first()
            if (blockedApps.isEmpty()) {
                Snackbar.make(binding.root, "No apps to block", Snackbar.LENGTH_SHORT).show()
                return@launch
            }
            val intent = Intent(requireContext(), AppBlockerService::class.java).apply {
                action = AppBlockerService.ACTION_START_BLOCKING
                putStringArrayListExtra(AppBlockerService.EXTRA_BLOCKED_PACKAGES, ArrayList(blockedApps.map { it.packageName }))
            }
            requireContext().startService(intent)
            Snackbar.make(binding.root, R.string.blocking_active, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun stopBlocking() {
        val intent = Intent(requireContext(), AppBlockerService::class.java).apply {
            action = AppBlockerService.ACTION_STOP_BLOCKING
        }
        requireContext().startService(intent)
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
        updateBlockingUI(AppBlockerService.isRunning)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class AppInfo(val packageName: String, val appName: String)
}

// Fixed Adapter Implementation with correct imports
class InstalledAppsAdapter(
    private val apps: List<BlockerFragment.AppInfo>,
    private val onAppSelected: (BlockerFragment.AppInfo, Boolean) -> Unit
) : RecyclerView.Adapter<InstalledAppsAdapter.ViewHolder>() {

    private val selectedApps = mutableSetOf<String>()

    inner class ViewHolder(val binding: ItemBlockedAppBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBlockedAppBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        holder.binding.tvAppName.text = app.appName
        holder.binding.switchBlocked.isChecked = selectedApps.contains(app.packageName)
        holder.binding.switchBlocked.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) selectedApps.add(app.packageName) else selectedApps.remove(app.packageName)
            onAppSelected(app, isChecked)
        }
        holder.binding.btnRemove.visibility = View.GONE
    }

    override fun getItemCount() = apps.size
}