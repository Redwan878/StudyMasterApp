/*
package com.porashona.studymaster.ui.blocker

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Process
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
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.BlockedApp
import com.porashona.studymaster.databinding.FragmentBlockerBinding
import com.porashona.studymaster.databinding.ItemBlockedAppBinding
import com.porashona.studymaster.service.AppBlockerService
import com.porashona.studymaster.utils.RootUtils
import com.porashona.studymaster.utils.ZenSessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
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

    /** Selected duration (minutes) for the Zen session, driven by the chip group. */
    private var selectedDurationMinutes: Int = ZenSessionManager.DEFAULT_DURATION_MINUTES

    /** Live countdown job — rebuilt whenever a new session starts. */
    private var countdownJob: Job? = null

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
        setupZenMode()
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
    }

    private fun setupZenMode() {
        // Build duration chips
        val group = binding.chipGroupDuration
        group.removeAllViews()
        ZenSessionManager.DURATION_PRESETS_MINUTES.forEach { minutes ->
            val chip = Chip(requireContext()).apply {
                id = View.generateViewId()
                text = getString(R.string.zen_duration_minutes, minutes)
                isCheckable = true
                isChecked = minutes == selectedDurationMinutes
                tag = minutes
            }
            group.addView(chip)
        }
        group.setOnCheckedStateChangeListener { _, checkedIds ->
            val id = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
            val chip = group.findViewById<Chip>(id) ?: return@setOnCheckedStateChangeListener
            val minutes = chip.tag as? Int ?: return@setOnCheckedStateChangeListener
            selectedDurationMinutes = minutes
            viewModel.setZenLastDurationMinutes(minutes)
        }

        binding.switchZenDnd.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setZenEnableDnd(isChecked)
            if (isChecked && !ZenSessionManager.isDndAccessGranted(requireContext())) {
                showDndPermissionDialog()
            }
        }

        binding.btnStartZen.setOnClickListener { startZenSession() }
        binding.btnStopZen.setOnClickListener { onStopZenClicked() }
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
                binding.tvZenSelectedApps.text =
                    getString(R.string.zen_selected_apps, apps.count { it.isBlocked })
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalBlockAttempts.collectLatest { count ->
                binding.tvBlockAttempts.text = "${count ?: 0}"
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.zenEnableDnd.collectLatest { enabled ->
                if (binding.switchZenDnd.isChecked != enabled) {
                    binding.switchZenDnd.isChecked = enabled
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.zenLastDurationMinutes.collectLatest { minutes ->
                if (selectedDurationMinutes != minutes) {
                    selectedDurationMinutes = minutes
                    // Re-check the matching chip
                    for (i in 0 until binding.chipGroupDuration.childCount) {
                        val chip = binding.chipGroupDuration.getChildAt(i) as? Chip ?: continue
                        if (chip.tag == minutes) {
                            chip.isChecked = true
                            break
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.zenSessionEndTime.collectLatest { endTime ->
                renderZenSessionState(endTime)
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

    private fun renderZenSessionState(endTime: Long) {
        val now = System.currentTimeMillis()
        val sessionActive = endTime > now
        binding.zenSetupGroup.visibility = if (sessionActive) View.GONE else View.VISIBLE
        binding.zenActiveGroup.visibility = if (sessionActive) View.VISIBLE else View.GONE

        countdownJob?.cancel()
        if (sessionActive) {
            // The stop button is always present but disabled until the timer ends;
            // this makes the "strict, can't escape" behaviour explicit in the UI.
            binding.btnStopZen.isEnabled = false
            binding.tvZenLockedHint.visibility = View.VISIBLE

            countdownJob = viewLifecycleOwner.lifecycleScope.launch {
                while (isActive) {
                    val remaining = endTime - System.currentTimeMillis()
                    if (remaining <= 0) {
                        binding.tvZenCountdown.text = "00:00"
                        binding.btnStopZen.isEnabled = true
                        binding.tvZenLockedHint.visibility = View.GONE
                        break
                    }
                    val totalSec = remaining / 1000
                    val hours = totalSec / 3600
                    val mins = (totalSec % 3600) / 60
                    val secs = totalSec % 60
                    binding.tvZenCountdown.text = if (hours > 0) {
                        String.format("%d:%02d:%02d", hours, mins, secs)
                    } else {
                        String.format("%02d:%02d", mins, secs)
                    }
                    delay(500)
                }
            }
        }
    }

    private fun checkPermissions() {
        val isAccessibilityEnabled = isAccessibilityServiceEnabled()
        val isUsageStatsGranted = isUsageStatsPermissionGranted()
        // Show the "grant accessibility" card if either permission is missing;
        // the button opens the right settings screen for whichever is missing.
        val missing = !isAccessibilityEnabled || !isUsageStatsGranted
        binding.cardAccessibility.visibility = if (missing) View.VISIBLE else View.GONE
    }

    private fun checkAndRequestPermissions() {
        if (!isAccessibilityServiceEnabled()) {
            showAccessibilityDialog()
            return
        }
        if (!isUsageStatsPermissionGranted()) {
            showUsageStatsDialog()
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = requireContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        return enabledServices.any { it.resolveInfo.serviceInfo.packageName == requireContext().packageName }
    }

    /**
     * The blocker service relies on UsageStatsManager to detect the foreground
     * app. Without PACKAGE_USAGE_STATS granted through system settings the
     * service silently fails (UsageEvents is always empty), so callers have to
     * check this explicitly — it is NOT a runtime permission.
     */
    private fun isUsageStatsPermissionGranted(): Boolean {
        val appOps = requireContext().getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager
            ?: return false
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                requireContext().packageName
            )
        } else {
            @Suppress("DEPRECATION")
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                requireContext().packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun showUsageStatsDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.usage_stats_required)
            .setMessage(R.string.usage_stats_description)
            .setPositiveButton(R.string.grant_permission) { _, _ -> openUsageAccessSettings() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun openUsageAccessSettings() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
            // Deep-link to this app's entry where possible so the user doesn't
            // have to scroll through every installed app.
            data = Uri.fromParts("package", requireContext().packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        runCatching { startActivity(intent) }.onFailure {
            // Some OEMs (Xiaomi/MIUI in particular) don't honour the package URI.
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
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

    private fun showDndPermissionDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.zen_dnd_permission_required)
            .setMessage(R.string.zen_dnd_desc)
            .setPositiveButton(R.string.zen_grant_dnd) { _, _ ->
                startActivity(ZenSessionManager.dndAccessSettingsIntent())
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
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

    private fun startZenSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            val blockedApps = viewModel.blockedApps.first().filter { it.isBlocked }
            if (blockedApps.isEmpty()) {
                Snackbar.make(binding.root, R.string.zen_no_apps_selected, Snackbar.LENGTH_SHORT).show()
                return@launch
            }
            if (!isAccessibilityServiceEnabled()) {
                Snackbar.make(binding.root, R.string.zen_accessibility_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.enable_accessibility) { openAccessibilitySettings() }
                    .show()
                return@launch
            }
            if (!isUsageStatsPermissionGranted()) {
                Snackbar.make(binding.root, R.string.usage_stats_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.grant_permission) { openUsageAccessSettings() }
                    .show()
                return@launch
            }
            val enableDnd = viewModel.zenEnableDnd.value
            if (enableDnd && !ZenSessionManager.isDndAccessGranted(requireContext())) {
                showDndPermissionDialog()
                return@launch
            }

            val durationMs = selectedDurationMinutes * 60 * 1000L
            val intent = Intent(requireContext(), AppBlockerService::class.java).apply {
                action = AppBlockerService.ACTION_START_BLOCKING
                putStringArrayListExtra(
                    AppBlockerService.EXTRA_BLOCKED_PACKAGES,
                    ArrayList(blockedApps.map { it.packageName })
                )
                putExtra(AppBlockerService.EXTRA_SESSION_DURATION, durationMs)
                putExtra(AppBlockerService.EXTRA_STRICT, true)
                putExtra(AppBlockerService.EXTRA_ENABLE_DND, enableDnd)
            }
            requireContext().startService(intent)
            Snackbar.make(binding.root, R.string.blocking_active, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun onStopZenClicked() {
        val endTime = viewModel.zenSessionEndTime.value
        if (endTime > System.currentTimeMillis()) {
            // Locked — this branch should be unreachable because the button is
            // disabled until the timer hits zero, but keep the safeguard in case
            // accessibility tools tap it.
            Snackbar.make(binding.root, R.string.zen_locked_hint, Snackbar.LENGTH_SHORT).show()
            return
        }
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
        countdownJob?.cancel()
        countdownJob = null
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

*/