package com.porashona.studymaster.ui.stats

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.databinding.FragmentStatsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StatsViewModel by viewModels {
        StatsViewModelFactory(getRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCharts()
        observeViewModel()
    }

    private fun getRepository(): StudyRepository =
        (requireActivity().application as StudyMasterApplication).studyRepository

    private fun setupCharts() {
        // Setup Pie Chart
        binding.pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(Color.TRANSPARENT)
            holeRadius = 50f
            transparentCircleRadius = 55f
            setDrawCenterText(true)
            centerText = "বিষয়সমূহ"
            setCenterTextSize(14f)
            setCenterTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))
            legend.isEnabled = true
            legend.textSize = 12f
            legend.textColor = ContextCompat.getColor(requireContext(), R.color.text_primary)
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(10f)
            animateY(1000, Easing.EaseInOutQuad)
        }

        // Setup Bar Chart
        binding.barChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setDrawBarShadow(false)
            setDrawValueAboveBar(true)
            setPinchZoom(false)
            setScaleEnabled(false)
            legend.isEnabled = false
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                textColor = ContextCompat.getColor(requireContext(), R.color.text_primary)
                textSize = 10f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                gridColor = ContextCompat.getColor(requireContext(), R.color.text_hint)
                textColor = ContextCompat.getColor(requireContext(), R.color.text_primary)
                axisMinimum = 0f
            }
            
            axisRight.isEnabled = false
            animateY(1000, Easing.EaseInOutQuad)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todayTime.collectLatest { seconds ->
                binding.tvTodayTime.text = formatTime(seconds)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weekTime.collectLatest { seconds ->
                binding.tvWeekTime.text = formatTime(seconds)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalTime.collectLatest { seconds ->
                binding.tvTotalTime.text = formatTime(seconds)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalSessions.collectLatest { count ->
                binding.tvTotalSessions.text = "$count টি"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentStreak.collectLatest { streak ->
                binding.tvCurrentStreak.text = "$streak দিন"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.longestStreak.collectLatest { streak ->
                binding.tvLongestStreak.text = "$streak দিন"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subjectTimeData.collectLatest { data ->
                updatePieChart(data)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weeklyData.collectLatest { data ->
                updateBarChart(data)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productivityScore.collectLatest { score ->
                binding.tvProductivityScore.text = "$score%"
                binding.progressProductivity.progress = score
            }
        }
    }

    private fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        return if (hours > 0) {
            "${hours}ঘ ${minutes}মি"
        } else {
            "${minutes} মিনিট"
        }
    }

    private fun updatePieChart(data: List<Pair<String, Float>>) {
        if (data.isEmpty()) {
            binding.pieChart.visibility = View.GONE
            binding.tvNoSubjectData.visibility = View.VISIBLE
            return
        }

        binding.pieChart.visibility = View.VISIBLE
        binding.tvNoSubjectData.visibility = View.GONE

        val colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.chart_1),
            ContextCompat.getColor(requireContext(), R.color.chart_2),
            ContextCompat.getColor(requireContext(), R.color.chart_3),
            ContextCompat.getColor(requireContext(), R.color.chart_4),
            ContextCompat.getColor(requireContext(), R.color.chart_5)
        )

        val entries = data.map { (name, value) ->
            PieEntry(value, name)
        }

        val dataSet = PieDataSet(entries, "").apply {
            this.colors = colors.take(entries.size)
            sliceSpace = 2f
            selectionShift = 5f
            valueTextSize = 12f
            valueTextColor = Color.WHITE
            valueFormatter = PercentFormatter(binding.pieChart)
        }

        binding.pieChart.data = PieData(dataSet)
        binding.pieChart.invalidate()
    }

    private fun updateBarChart(data: List<Pair<String, Float>>) {
        if (data.isEmpty()) {
            binding.barChart.visibility = View.GONE
            return
        }

        binding.barChart.visibility = View.VISIBLE

        val entries = data.mapIndexed { index, (_, value) ->
            BarEntry(index.toFloat(), value)
        }

        val labels = data.map { it.first }

        val dataSet = BarDataSet(entries, "").apply {
            color = ContextCompat.getColor(requireContext(), R.color.primary)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.text_primary)
            valueTextSize = 10f
        }

        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.barChart.data = BarData(dataSet).apply {
            barWidth = 0.6f
        }
        binding.barChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}