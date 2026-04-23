package com.porashona.studymaster.ui.music

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.MusicCategory
import com.porashona.studymaster.data.model.MusicTrack
import com.porashona.studymaster.data.model.StudyMusicLibrary
import com.porashona.studymaster.databinding.FragmentMusicBinding
import com.porashona.studymaster.service.MusicService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

    private var musicService: MusicService? = null
    private var isBound = false

    private lateinit var adapter: MusicTrackAdapter
    private lateinit var preferencesManager: com.porashona.studymaster.data.preferences.PreferencesManager

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isBound = true
            observeMusicService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesManager = (requireActivity().application as StudyMasterApplication).preferencesManager

        setupRecyclerView()
        setupControls()
        observeSettings()
        bindMusicService()
    }

    private fun setupRecyclerView() {
        adapter = MusicTrackAdapter { track ->
            onTrackSelected(track)
        }

        binding.recyclerTracks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MusicFragment.adapter
        }

        adapter.submitList(StudyMusicLibrary.tracks)

        binding.chipGroupMusicCategory.setOnCheckedStateChangeListener { _, checked ->
            val category = when (checked.firstOrNull()) {
                R.id.chipMusicLofi -> MusicCategory.LOFI
                R.id.chipMusicNature -> MusicCategory.NATURE
                R.id.chipMusicClassical -> MusicCategory.CLASSICAL
                R.id.chipMusicAmbient -> MusicCategory.AMBIENT
                R.id.chipMusicJazz -> MusicCategory.JAZZ
                else -> null
            }
            val list = if (category == null) StudyMusicLibrary.tracks
            else StudyMusicLibrary.tracks.filter { it.category == category }
            adapter.submitList(list)
        }
    }

    private fun setupControls() {
        // Play/Pause button
        binding.btnPlayPause.setOnClickListener {
            if (musicService?.isCurrentlyPlaying() == true) {
                musicService?.pause()
            } else {
                musicService?.play()
            }
        }

        // Previous button
        binding.btnPrevious.setOnClickListener {
            musicService?.playPrevious()
        }

        // Next button
        binding.btnNext.setOnClickListener {
            musicService?.playNext()
        }

        // Stop button
        binding.btnStop.setOnClickListener {
            musicService?.stop()
            updatePlayPauseButton(false)
        }

        // Volume SeekBar
        binding.seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val volume = progress / 100f
                    musicService?.setVolume(volume)
                    viewLifecycleOwner.lifecycleScope.launch {
                        preferencesManager.setMusicVolume(volume)
                    }
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Music Enable Switch
        binding.switchEnableMusic.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                preferencesManager.setMusicEnabled(isChecked)
            }
            updateMusicEnabledUI(isChecked)

            if (!isChecked) {
                musicService?.stop()
            }
        }

        // Auto-play Switch
        binding.switchAutoPlay.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                preferencesManager.setAutoPlayMusic(isChecked)
            }
        }
    }

    private fun observeSettings() {
        viewLifecycleOwner.lifecycleScope.launch {
            preferencesManager.musicEnabled.collectLatest { enabled ->
                binding.switchEnableMusic.isChecked = enabled
                updateMusicEnabledUI(enabled)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            preferencesManager.musicVolume.collectLatest { volume ->
                binding.seekBarVolume.progress = (volume * 100).toInt()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            preferencesManager.autoPlayMusic.collectLatest { autoPlay ->
                binding.switchAutoPlay.isChecked = autoPlay
            }
        }
    }

    private fun observeMusicService() {
        viewLifecycleOwner.lifecycleScope.launch {
            musicService?.isPlaying?.collectLatest { isPlaying ->
                updatePlayPauseButton(isPlaying)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            musicService?.currentTrack?.collectLatest { track ->
                updateCurrentTrackUI(track)
                adapter.setSelectedTrack(track?.id)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            musicService?.isLoading?.collectLatest { isLoading ->
                binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            musicService?.error?.collectLatest { error ->
                error?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onTrackSelected(track: MusicTrack) {
        viewLifecycleOwner.lifecycleScope.launch {
            val musicEnabled = preferencesManager.musicEnabled.first()
            if (!musicEnabled) {
                Snackbar.make(
                    binding.root,
                    "প্রথমে মিউজিক চালু করুন",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@launch
            }

            preferencesManager.setSelectedTrackId(track.id)
            musicService?.playTrack(track.id)
        }
    }

    private fun updatePlayPauseButton(isPlaying: Boolean) {
        binding.btnPlayPause.setImageResource(
            if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        )
    }

    private fun updateCurrentTrackUI(track: MusicTrack?) {
        if (track != null) {
            binding.tvCurrentTrackTitle.text = track.title
            binding.tvCurrentTrackArtist.text = track.artist
            binding.cardNowPlaying.visibility = View.VISIBLE
        } else {
            binding.cardNowPlaying.visibility = View.GONE
        }
    }

    private fun updateMusicEnabledUI(enabled: Boolean) {
        binding.controlsContainer.alpha = if (enabled) 1f else 0.5f
        binding.recyclerTracks.alpha = if (enabled) 1f else 0.5f

        binding.btnPlayPause.isEnabled = enabled
        binding.btnPrevious.isEnabled = enabled
        binding.btnNext.isEnabled = enabled
        binding.btnStop.isEnabled = enabled
        binding.seekBarVolume.isEnabled = enabled
    }

    private fun bindMusicService() {
        Intent(requireContext(), MusicService::class.java).also { intent ->
            requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isBound) {
            requireContext().unbindService(serviceConnection)
            isBound = false
        }
        _binding = null
    }
}