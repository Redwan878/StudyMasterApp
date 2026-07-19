package com.porashona.studymaster.ui.assistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.databinding.FragmentAssistantBinding
import com.porashona.studymaster.databinding.ItemAssistantSuggestionBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AssistantFragment : Fragment() {

    private var _binding: FragmentAssistantBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AssistantViewModel by viewModels {
        val app = requireActivity().application as StudyMasterApplication
        AssistantViewModelFactory(app.studyRepository, app.extendedRepository)
    }

    private lateinit var adapter: SuggestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssistantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SuggestionAdapter()
        binding.recyclerSuggestions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSuggestions.adapter = adapter

        binding.fabRefresh.setOnClickListener {
            // The Flow is driven off Room + repository Flows, so nothing forces
            // a re-collect; poking the adapter ensures transient UI state resets.
            adapter.notifyDataSetChanged()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.suggestions.collectLatest { list ->
                binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                adapter.submitList(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class SuggestionAdapter :
        ListAdapter<StudyAssistantEngine.Suggestion, SuggestionAdapter.VH>(DIFF) {

        class VH(val binding: ItemAssistantSuggestionBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflater = LayoutInflater.from(parent.context)
            return VH(ItemAssistantSuggestionBinding.inflate(inflater, parent, false))
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = getItem(position)
            val b = holder.binding
            b.tvTitle.text = item.title
            b.tvBody.text = item.body
            val (label, colorRes) = when (item.kind) {
                StudyAssistantEngine.Kind.NEXT -> b.root.context.getString(R.string.assistant_section_next) to R.color.primary
                StudyAssistantEngine.Kind.INSIGHT -> b.root.context.getString(R.string.assistant_section_insights) to R.color.info
                StudyAssistantEngine.Kind.NUDGE -> b.root.context.getString(R.string.assistant_section_insights) to R.color.warning
            }
            b.tvKind.text = label
            b.vKindIndicator.setBackgroundColor(b.root.context.getColor(colorRes))
        }

        companion object {
            private val DIFF = object : DiffUtil.ItemCallback<StudyAssistantEngine.Suggestion>() {
                override fun areItemsTheSame(
                    oldItem: StudyAssistantEngine.Suggestion,
                    newItem: StudyAssistantEngine.Suggestion
                ): Boolean = oldItem.title == newItem.title && oldItem.kind == newItem.kind

                override fun areContentsTheSame(
                    oldItem: StudyAssistantEngine.Suggestion,
                    newItem: StudyAssistantEngine.Suggestion
                ): Boolean = oldItem == newItem
            }
        }
    }
}
