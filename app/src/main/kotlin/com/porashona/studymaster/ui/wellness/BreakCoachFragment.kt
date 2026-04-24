package com.porashona.studymaster.ui.wellness

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import com.porashona.studymaster.R
import com.porashona.studymaster.databinding.FragmentBreakCoachBinding

/**
 * Break Coach — surfaces bite-sized wellness exercises the user can do on any
 * break: 4-7-8 breathing (with an animated expanding/contracting circle),
 * stretching sequence, 20-20-20 eye exercise, hydration reminder and walking
 * / healthy-snack suggestions.
 */
class BreakCoachFragment : Fragment() {

    private var _binding: FragmentBreakCoachBinding? = null
    private val binding get() = _binding!!

    private var breathAnimator: ValueAnimator? = null
    private var breathRunning = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBreakCoachBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvStretch.text = getString(R.string.break_stretch_body)
        binding.tvEye.text = getString(R.string.break_eye_body)
        binding.tvHydration.text = getString(R.string.break_hydration_body)
        binding.tvWalk.text = getString(R.string.break_walk_body)
        binding.tvSnack.text = getString(R.string.break_snack_body)

        binding.btnBreathToggle.setOnClickListener { toggleBreath() }
    }

    private fun toggleBreath() {
        if (breathRunning) {
            stopBreath()
        } else {
            startBreath()
        }
    }

    private fun startBreath() {
        breathRunning = true
        binding.btnBreathToggle.text = getString(R.string.break_breathing_stop)
        // 4-7-8 breathing: inhale 4s, hold 7s, exhale 8s. Total cycle = 19s.
        val cycleMs = 19_000L
        breathAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = cycleMs
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { a ->
                val frac = a.animatedValue as Float
                val phaseLabel: Int
                val scale: Float
                when {
                    frac < 4f / 19f -> {
                        phaseLabel = R.string.break_breathing_inhale
                        val p = frac / (4f / 19f)
                        scale = 0.6f + 0.5f * p
                    }
                    frac < 11f / 19f -> {
                        phaseLabel = R.string.break_breathing_hold
                        scale = 1.1f
                    }
                    else -> {
                        phaseLabel = R.string.break_breathing_exhale
                        val p = (frac - 11f / 19f) / (8f / 19f)
                        scale = 1.1f - 0.5f * p
                    }
                }
                binding.breathCircle.scaleX = scale
                binding.breathCircle.scaleY = scale
                binding.tvBreathPhase.setText(phaseLabel)
            }
            start()
        }
    }

    private fun stopBreath() {
        breathRunning = false
        breathAnimator?.cancel()
        breathAnimator = null
        binding.btnBreathToggle.text = getString(R.string.break_breathing_start)
        binding.tvBreathPhase.setText(R.string.break_breathing_ready)
        binding.breathCircle.scaleX = 1f
        binding.breathCircle.scaleY = 1f
    }

    override fun onPause() {
        super.onPause()
        if (breathRunning) stopBreath()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        breathAnimator?.cancel()
        _binding = null
    }
}
