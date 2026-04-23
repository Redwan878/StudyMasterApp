package com.porashona.studymaster.ui.quotes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.Quote
import com.porashona.studymaster.data.repository.ExtendedRepository
import com.porashona.studymaster.databinding.FragmentQuotesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class QuotesFragment : Fragment() {

    private var _binding: FragmentQuotesBinding? = null
    private val binding get() = _binding!!

    private val repo: ExtendedRepository by lazy {
        (requireActivity().application as StudyMasterApplication).extendedRepository
    }

    private enum class Filter { ALL, FAVORITES, CUSTOM }
    private val filter = MutableStateFlow(Filter.ALL)

    private lateinit var adapter: QuoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = QuoteAdapter(
            onFavorite = { q -> lifecycleScope.launch { repo.toggleQuoteFavorite(q.id) } },
            onShare = { shareQuote(it) },
            onDelete = { confirmDelete(it) },
        )
        binding.recyclerQuotes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerQuotes.adapter = adapter

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                filter.value = when (tab.position) {
                    1 -> Filter.FAVORITES
                    2 -> Filter.CUSTOM
                    else -> Filter.ALL
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) = Unit
            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })

        binding.fabAddQuote.setOnClickListener { showAddDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                filter.flatMapLatest { f -> sourceFor(f) }.collect { list ->
                    adapter.submitList(list)
                    binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                    binding.tvEmpty.text = when (filter.value) {
                        Filter.FAVORITES -> getString(R.string.quote_empty_favorites)
                        Filter.CUSTOM -> getString(R.string.quote_empty_custom)
                        else -> ""
                    }
                }
            }
        }
    }

    private fun sourceFor(f: Filter): Flow<List<Quote>> = when (f) {
        Filter.FAVORITES -> repo.favoriteQuotes
        Filter.CUSTOM -> repo.customQuotes
        else -> repo.allQuotes
    }

    private fun shareQuote(q: Quote) {
        val author = q.authorBn.ifBlank { q.author }
        val body = buildString {
            append("\u201C").append(q.textBn).append("\u201D")
            if (q.textEn.isNotBlank()) append("\n\n\u201C").append(q.textEn).append("\u201D")
            if (author.isNotBlank()) append("\n— ").append(author)
        }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, body)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.quote_share_chooser)))
    }

    private fun confirmDelete(q: Quote) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.action_delete)
            .setMessage(q.textBn)
            .setNegativeButton(R.string.action_close, null)
            .setPositiveButton(R.string.action_delete) { _, _ ->
                lifecycleScope.launch { repo.deleteQuote(q) }
            }
            .show()
    }

    private fun showAddDialog() {
        val container = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            val pad = (resources.displayMetrics.density * 16).toInt()
            setPadding(pad, pad, pad, 0)
        }
        val bnLayout = TextInputLayout(requireContext()).apply {
            hint = getString(R.string.custom_quote_text_bn)
        }
        val bn = TextInputEditText(bnLayout.context).apply { minLines = 2 }
        bnLayout.addView(bn)
        val enLayout = TextInputLayout(requireContext()).apply {
            hint = getString(R.string.custom_quote_text_en)
        }
        val en = TextInputEditText(enLayout.context).apply { minLines = 2 }
        enLayout.addView(en)
        val authorLayout = TextInputLayout(requireContext()).apply {
            hint = getString(R.string.custom_quote_author)
        }
        val author = TextInputEditText(authorLayout.context)
        authorLayout.addView(author)

        listOf(bnLayout, enLayout, authorLayout).forEach {
            container.addView(
                it,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ),
            )
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.add_custom_quote)
            .setView(container)
            .setNegativeButton(R.string.action_close, null)
            .setPositiveButton(R.string.action_save) { _, _ ->
                val bnText = bn.text?.toString()?.trim().orEmpty()
                val enText = en.text?.toString()?.trim().orEmpty()
                val authorText = author.text?.toString()?.trim().orEmpty()
                if (bnText.isBlank() && enText.isBlank()) return@setPositiveButton
                lifecycleScope.launch {
                    repo.addCustomQuote(
                        textEn = enText,
                        textBn = bnText.ifBlank { enText },
                        author = authorText,
                    )
                }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
