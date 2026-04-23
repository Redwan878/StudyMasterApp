package com.porashona.studymaster.ui.randomizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.DefaultSubjects
import com.porashona.studymaster.databinding.FragmentRandomizerBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RandomizerFragment : Fragment() {
    private var _binding: FragmentRandomizerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSpin.setOnClickListener { spin() }
    }

    /**
     * Pick from the user's own subjects when they have any; otherwise fall back
     * to the bundled SSC science list so the feature still works on first
     * launch. Previously this always used the hardcoded list, which made the
     * screen feel unrelated to the user's actual study setup.
     */
    private fun spin() {
        viewLifecycleOwner.lifecycleScope.launch {
            val app = requireActivity().application as StudyMasterApplication
            val userSubjects = app.database.subjectDao().getAllSubjects().first()
            val names: List<String> = when {
                userSubjects.isNotEmpty() -> userSubjects.map { it.name }
                else -> DefaultSubjects.sscScienceSubjects.map { it.name }
            }
            val picked = names.random()
            binding.tvResult.text = picked
            binding.tvResult.alpha = 0f
            binding.tvResult.animate().alpha(1f).setDuration(500).start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
