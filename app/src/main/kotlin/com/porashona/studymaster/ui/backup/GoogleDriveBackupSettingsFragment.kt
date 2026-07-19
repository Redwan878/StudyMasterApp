package com.porashona.studymaster.ui.backup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.backup.GoogleDriveBackupManager
import com.porashona.studymaster.databinding.FragmentGoogleDriveBackupBinding
import com.porashona.studymaster.ui.backup.adapter.BackupListAdapter
import kotlinx.coroutines.launch

class GoogleDriveBackupSettingsFragment : Fragment() {
    private var _binding: FragmentGoogleDriveBackupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GoogleDriveBackupViewModel by viewModels {
        GoogleDriveBackupViewModelFactory(
            (requireActivity().application as StudyMasterApplication).backupManager
        )
    }

    private val backupManager by lazy { GoogleDriveBackupManager() }
    private lateinit var backupListAdapter: BackupListAdapter

    private val pickFileLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onBackupFileSelected(it) }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onGoogleDrivePermissionGranted()
        } else {
            showPermissionRequiredDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoogleDriveBackupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackupList()
        setupClickListeners()
        observeViewModel()
        viewModel.loadBackupStatus()
        viewModel.loadBackupConfig()
    }

    private fun setupBackupList() {
        backupListAdapter = BackupListAdapter(
            onBackupClick = { backupInfo ->
                showBackupOptionsDialog(backupInfo)
            },
            onBackupDeleteClick = { backupInfo ->
                showDeleteBackupConfirmation(backupInfo)
            }
        )

        binding.recyclerBackupList.apply {
            adapter = backupListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupClickListeners() {
        binding.btnBackupNow.setOnClickListener {
            showBackupConfirmationDialog()
        }

        binding.btnRestore.setOnClickListener {
            if (backupManager.isDriveAuthenticated()) {
                showRestoreBackupDialog()
            } else {
                requestGoogleDrivePermission()
            }
        }

        binding.btnSync.setOnClickListener {
            viewModel.syncBackupsToDrive()
        }

        binding.btnConfigure.setOnClickListener {
            showBackupConfigurationDialog()
        }

        binding.switchAutoBackup.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateBackupConfig { it.copy(autoBackup = isChecked) }
        }

        binding.switchBackupWifiOnly.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateBackupConfig { it.copy(backupWifiOnly = isChecked) }
        }

        binding.switchKeepLocalBackup.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateBackupConfig { it.copy(keepLocalBackup = isChecked) }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.backupStatus.collect { status ->
                updateBackupStatusUI(status)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.backupConfig.collect { config ->
                updateBackupConfigUI(config)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.backupList.collect { backups ->
                backupListAdapter.submitList(backups)
                updateEmptyStateView(backups.isEmpty())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.backupOperationResult.collect { result ->
                handleBackupOperationResult(result)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let { showErrorMessage(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isGoogleDriveAuthenticated.collect { isAuthenticated ->
                updateDriveAuthenticationUI(isAuthenticated)
            }
        }
    }

    private fun showBackupConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("নতুন ব্যাকআপ তৈরি করুন")
            .setMessage("আপনি কি কনফার্ম করেন যে আপনি নতুন ব্যাকআপ তৈরি করতে চান? এটি বর্তমান ডেটা একটি নতুন ফাইলে সংরক্ষণ করবে।")
            .setPositiveButton("হ্যাঁ, ব্যাকআপ তৈরি করুন") { _, _ ->
                viewModel.createBackup()
            }
            .setNegativeButton("ক্যান্সেল", null)
            .show()
    }

    private fun showRestoreBackupDialog() {
        viewModel.loadBackupsFromDrive()
        if (backupListAdapter.itemCount == 0) {
            showNoBackupsAvailableDialog()
        } else {
            showBackupSelectionDialog()
        }
    }

    private fun showBackupOptionsDialog(backupInfo: GoogleDriveBackupManager.DriveBackupInfo) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(backupInfo.name)
            .setMessage("""

                তারিখ: ${formatTimestamp(backupInfo.createdTime)}
                সাইজ: ${formatFileSize(backupInfo.size)}
                ভার্সন: v${backupInfo.version}
                অ্যাপ: ${backupInfo.appVersion}

                কী করতে চান?
                """)
            .setPositiveButton("ডাউনলোড করুন") { _, _ ->
                showDownloadBackupConfirmation(backupInfo)
            }
            .setNeutralButton("মুছুন") { _, _ ->
                showDeleteBackupConfirmation(backupInfo)
            }
            .setNegativeButton("ক্যান্সেল", null)
            .show()
    }

    private fun showDeleteBackupConfirmation(backupInfo: GoogleDriveBackupManager.DriveBackupInfo) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ব্যাকআপ মুছুন")
            .setMessage("আপনি কি নিশ্চিত যে আপনি "${backupInfo.name}" মুছতে চান? এই কাজটি পূণরায় করা যাবে না।")
            .setPositiveButton("হ্যাঁ, মুছুন") { _, _ ->
                viewModel.deleteBackupFromDrive(backupInfo.fileId)
            }
            .setNegativeButton("ক্যান্সেল", null)
            .show()
    }

    private fun showDownloadBackupConfirmation(backupInfo: GoogleDriveBackupManager.DriveBackupInfo) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ব্যাকআপ ডাউনলোড করুন")
            .setMessage("আপনি কি নিশ্চিত যে আপনি "${backupInfo.name}" ডাউনলোড করতে চান?")
            .setPositiveButton("ডাউনলোড করুন") { _, _ ->
                viewModel.downloadBackupFromDrive(backupInfo.fileId)
            }
            .setNegativeButton("ক্যান্সেল", null)
            .show()
    }

    private fun showBackupConfigurationDialog() {
        viewModel.backupConfig.value?.let { config ->
            val builder = MaterialAlertDialogBuilder(requireContext())
                .setTitle("ব্যাকআপ কনফিগারেশন")

            val view = layoutInflater.inflate(R.layout.dialog_backup_config, null)

            val switchAutoBackup = view.findViewById<android.widget.Switch>(R.id.switchAutoBackup)
            val spinnerBackupFrequency = view.findViewById<android.widget.Spinner>(R.id.spinnerBackupFrequency)
            val spinnerBackupLocation = view.findViewById<android.widget.Spinner>(R.id.spinnerBackupLocation)
            val switchBackupWifiOnly = view.findViewById<android.widget.Switch>(R.id.switchBackupWifiOnly)
            val switchKeepLocalBackup = view.findViewById<android.widget.Switch>(R.id.switchKeepLocalBackup)
            val seekBarMaxBackups = view.findViewById<android.widget.SeekBar>(R.id.seekBarMaxBackups)
            val textMaxBackups = view.findViewById<android.widget.TextView>(R.id.textMaxBackups)

            switchAutoBackup.isChecked = config.autoBackup
            switchBackupWifiOnly.isChecked = config.backupWifiOnly
            switchKeepLocalBackup.isChecked = config.keepLocalBackup
            seekBarMaxBackups.progress = config.maxBackups - 1
            textMaxBackups.text = config.maxBackups.toString()

            seekBarMaxBackups.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                    val maxBackups = progress + 1
                    textMaxBackups.text = maxBackups.toString()
                    viewModel.updateBackupConfig { it.copy(maxBackups = maxBackups) }
                }
                override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
            })

            builder.setView(view)
                .setPositiveButton("সেভ") { _, _ ->
                    viewModel.updateBackupConfig { config.copy(
                        autoBackup = switchAutoBackup.isChecked,
                        backupWifiOnly = switchBackupWifiOnly.isChecked,
                        keepLocalBackup = switchKeepLocalBackup.isChecked
                    ) }
                }
                .setNegativeButton("ক্যান্সেল", null)
                .show()
        }
    }

    private fun showPermissionRequiredDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Google Drive অ্যাক্সেসের প্রয়োজন")
            .setMessage("স্টাডিমাস্টার অ্যাপটি আপনার গুগল ড্রাইভে ব্যাকআপ করতে গুগল ড্রাইভ অ্যাক্সেসের জন্য অনুমতির প্রয়োজন।")
            .setPositiveButton("অনুমতি দিন") { _, _ ->
                requestGoogleDrivePermission()
            }
            .setNegativeButton("ক্যান্সেল") { _, _ ->
                showToast("Google Drive অ্যাক্সেস না দিলে ব্যাকআপ ও রিস্টোর করার সুবিধা হবে না")
            }
            .show()
    }

    private fun showNoBackupsAvailableDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("কোনো ব্যাকআপ নেই")
            .setMessage("Google Drive-এ কোনো ব্যাকআপ পাওয়া যায়নি। নতুন ব্যাকআপ তৈরি করতে 'ব্যাকআপ এখন' বাটনে ট্যাপ করুন।")
            .setPositiveButton("ওকে", null)
            .show()
    }

    private fun updateBackupStatusUI(status: GoogleDriveBackupManager.BackupStatus) {
        binding.apply {
            tvBackupStatus.text = status.syncStatus.uppercase()
            tvBackupStatus.setTextColor(
                when (status.syncStatus) {
                    "healthy" -> getColor(R.color.success_green)
                    "syncing" -> getColor(R.color.primary_purple)
                    "error" -> getColor(R.color.error_red)
                    else -> getColor(R.color.on_surface_variant)
                }
            )

            tvLastBackupTime.text = formatTimestamp(status.lastBackupTime)
            tvBackupSize.text = formatFileSize(status.backupSize)

            btnBackupNow.isEnabled = !status.isBackupInProgress
            btnSync.isEnabled = !status.isBackupInProgress && status.isCloudSyncEnabled
            btnRestore.isEnabled = status.isCloudSyncEnabled
        }
    }

    private fun updateBackupConfigUI(config: GoogleDriveBackupManager.BackupConfig) {
        binding.apply {
            switchAutoBackup.isChecked = config.autoBackup
            switchBackupWifiOnly.isChecked = config.backupWifiOnly
            switchKeepLocalBackup.isChecked = config.keepLocalBackup

            tvBackupFrequency.text = when (config.backupFrequency) {
                GoogleDriveBackupManager.BackupFrequency.ON_DEMAND -> "On Demand"
                GoogleDriveBackupManager.BackupFrequency.HOURLY -> "Every Hour"
                GoogleDriveBackupManager.BackupFrequency.DAILY -> "Daily"
                GoogleDriveBackupManager.BackupFrequency.WEEKLY -> "Weekly"
            }

            tvBackupLocation.text = when (config.backupLocation) {
                GoogleDriveBackupManager.BackupLocation.LOCAL_ONLY -> "Local Storage Only"
                GoogleDriveBackupManager.BackupLocation.GOOGLE_DRIVE_ONLY -> "Google Drive Only"
                GoogleDriveBackupManager.BackupLocation.BOTH -> "Local + Google Drive"
            }
        }
    }

    private fun updateEmptyStateView(isEmpty: Boolean) {
        binding.apply {
            recyclerBackupList.visibility = if (isEmpty) View.GONE else View.VISIBLE
            tvEmptyBackups.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }

    private fun updateDriveAuthenticationUI(isAuthenticated: Boolean) {
        binding.apply {
            ivDriveAuthenticationStatus.setImageResource(
                if (isAuthenticated) R.drawable.ic_drive_authenticated else R.drawable.ic_drive_not_authenticated
            )

            tvDriveAuthenticationStatus.text = if (isAuthenticated) {
                "Google Drive অ্যাক্সেস করা আছে"
            } else {
                "Google Drive অ্যাক্সেস নেই - ব্যাকআপ জন্য অনুমতি প্রয়োজন"
            }

            tvDriveAuthenticationStatus.setTextColor(
                if (isAuthenticated) getColor(R.color.success_green) else getColor(R.color.error_red)
            )

            btnRestore.isEnabled = isAuthenticated
            btnSync.isEnabled = isAuthenticated && viewModel.backupStatus.value?.isHealthy == true
        }
    }

    private fun handleBackupOperationResult(result: GoogleDriveBackupManager.SyncResult) {
        when {
            result.success -> {
                showToast(result.message)
                viewModel.loadBackupStatus()
                viewModel.loadBackupsFromDrive()
            }
            else -> {
                showErrorMessage(result.message)
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = android.text.format.SimpleDateFormat("dd MMM yyyy, hh:mm aa", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }

    private fun formatFileSize(size: Long): String {
        if (size == 0L) return "0 KB"

        val units = arrayOf("B", "KB", "MB", "GB")
        var sizeValue = size.toDouble()
        var unitIndex = 0

        while (sizeValue >= 1024.0 && unitIndex < units.size - 1) {
            sizeValue /= 1024.0
            unitIndex++
        }

        return String.format("%.1f %s", sizeValue, units[unitIndex])
    }

    private fun showBackupSelectionDialog() {
        viewModel.backupList.value?.let { backups ->
            if (backups.isEmpty()) {
                showNoBackupsAvailableDialog()
                return
            }

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("ব্যাকআপ নির্বাচন করুন")
                .setSingleChoiceItems(
                    backups.map { backup -> backup.name }.toTypedArray(),
                    -1
                ) { dialog, which ->
                    val selectedBackup = backups[which]
                    showRestoreBackupConfirmation(selectedBackup)
                    dialog.dismiss()
                }
                .setNegativeButton("ক্যান্সেল", null)
                .show()
        }
    }

    private fun showRestoreBackupConfirmation(backupInfo: GoogleDriveBackupManager.DriveBackupInfo) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ব্যাকআপ রিস্টোর করুন")
            .setMessage("""

                আপনি "${backupInfo.name}" রিস্টোর করতে চান?

                এই কাজটি বর্তমান ডেটার জন্য একটি পূণরায় ফর্ম তৈরি করবে। এই কাজটি পূণরায় করা যাবে না।
                """)
            .setPositiveButton("হ্যাঁ, রিস্টোর করুন") { _, _ ->
                viewModel.restoreBackupFromDrive(backupInfo.fileId)
            }
            .setNegativeButton("ক্যান্সেল", null)
            .show()
    }

    private fun requestGoogleDrivePermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.GET_ACCOUNTS)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        backupManager.cleanup()
    }
}

class GoogleDriveBackupViewModelFactory(
    private val backupManager: GoogleDriveBackupManager
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoogleDriveBackupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoogleDriveBackupViewModel(backupManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class GoogleDriveBackupViewModel(
    private val backupManager: GoogleDriveBackupManager
) : androidx.lifecycle.ViewModel() {

    private val _backupStatus = androidx.lifecycle.MutableLiveData<GoogleDriveBackupManager.BackupStatus>()
    val backupStatus: androidx.lifecycle.LiveData<GoogleDriveBackupManager.BackupStatus> = _backupStatus

    private val _backupConfig = androidx.lifecycle.MutableLiveData<GoogleDriveBackupManager.BackupConfig>()
    val backupConfig: androidx.lifecycle.LiveData<GoogleDriveBackupManager.BackupConfig> = _backupConfig

    private val _backupList = androidx.lifecycle.MutableLiveData<List<GoogleDriveBackupManager.DriveBackupInfo>>()
    val backupList: androidx.lifecycle.LiveData<List<GoogleDriveBackupManager.DriveBackupInfo>> = _backupList

    private val _error = androidx.lifecycle.MutableLiveData<String?>()
    val error: androidx.lifecycle.LiveData<String?> = _error

    private val _isGoogleDriveAuthenticated = androidx.lifecycle.MutableLiveData<Boolean>()
    val isGoogleDriveAuthenticated: androidx.lifecycle.LiveData<Boolean> = _isGoogleDriveAuthenticated

    private val _backupOperationResult = androidx.lifecycle.MutableLiveData<GoogleDriveBackupManager.SyncResult>()
    val backupOperationResult: androidx.lifecycle.LiveData<GoogleDriveBackupManager.SyncResult> = _backupOperationResult

    fun loadBackupStatus() {
        _isGoogleDriveAuthenticated.value = backupManager.isDriveAuthenticated()
        _backupStatus.value = backupManager.getBackupStatus()
    }

    fun loadBackupConfig() {
        _backupConfig.value = backupManager.getBackupConfig()
    }

    fun updateBackupConfig(update: (GoogleDriveBackupManager.BackupConfig) -> GoogleDriveBackupManager.BackupConfig) {
        _backupConfig.value = update(_backupConfig.value ?: GoogleDriveBackupManager.BackupConfig())
        backupManager.updateBackupConfig(_backupConfig.value ?: GoogleDriveBackupManager.BackupConfig())
    }

    fun loadBackupsFromDrive() {
        if (!backupManager.isDriveAuthenticated()) {
            _error.value = "Google Drive অ্যাক্সেস নেই"
            return
        }

        viewModelScope.launch {
            try {
                _backupList.value = backupManager.getBackupList()
            } catch (e: Exception) {
                _error.value = "ব্যাকআপ লোড করতে ব্যর্থ: ${e.message}"
            }
        }
    }

    fun createBackup() {
        if (!backupManager.isDriveAuthenticated()) {
            _error.value = "Google Drive অ্যাক্সেস নেই"
            return
        }

        viewModelScope.launch {
            _backupOperationResult.value = backupManager.uploadBackupToDrive()
            loadBackupStatus()
        }
    }

    fun syncBackupsToDrive() {
        if (!backupManager.isDriveAuthenticated()) {
            _error.value = "Google Drive অ্যাক্সেস নেই"
            return
        }

        viewModelScope.launch {
            _backupOperationResult.value = backupManager.syncAllBackups()
            loadBackupStatus()
        }
    }

    fun downloadBackupFromDrive(fileId: String) {
        viewModelScope.launch {
            _backupOperationResult.value = backupManager.downloadBackupFromDrive(fileId)
            loadBackupStatus()
        }
    }

    fun restoreBackupFromDrive(fileId: String) {
        viewModelScope.launch {
            _backupOperationResult.value = backupManager.restoreBackupFromDrive(fileId)
            loadBackupStatus()
        }
    }

    fun deleteBackupFromDrive(fileId: String) {
        viewModelScope.launch {
            val result = backupManager.deleteBackupFromDrive(fileId)
            _backupOperationResult.value = result
            loadBackupStatus()
        }
    }

    fun onGoogleDrivePermissionGranted() {
        backupManager.initializeDriveClient()
        loadBackupStatus()
    }

    fun onBackupFileSelected(uri: android.net.Uri) {
        // Handle backup file selection from local storage
        viewModelScope.launch {
            try {
                val result = backupManager.restoreBackupFromDrive(uri)
                _backupOperationResult.value = result
                loadBackupStatus()
            } catch (e: Exception) {
                _error.value = "ব্যাকআপ রিস্টোর করতে ব্যর্থ: ${e.message}"
            }
        }
    }

    fun forceBackupAndSync() {
        if (!backupManager.isDriveAuthenticated()) {
            _error.value = "Google Drive অ্যাক্সেস নেই"
            return
        }

        viewModelScope.launch {
            _backupOperationResult.value = backupManager.forceBackupAndSync()
            loadBackupStatus()
        }
    }

    fun getOrCreateBackupFolder(): String? {
        return backupManager.getOrCreateBackupFolder()
    }

    fun cleanup() {
        backupManager.cleanup()
    }
}