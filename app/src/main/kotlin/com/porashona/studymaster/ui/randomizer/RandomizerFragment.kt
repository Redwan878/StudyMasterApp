package com.porashona.studymaster.ui.randomizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.porashona.studymaster.data.model.DefaultSubjects
import com.porashona.studymaster.databinding.FragmentRandomizerBinding

class RandomizerFragment : Fragment() {
    private lateinit var binding: FragmentRandomizerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRandomizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSpin.setOnClickListener {
            val randomSubject = DefaultSubjects.sscScienceSubjects.random()
            binding.tvResult.text = randomSubject.name
            binding.tvResult.alpha = 0f
            binding.tvResult.animate().alpha(1f).setDuration(500).start()
        }
    }
}