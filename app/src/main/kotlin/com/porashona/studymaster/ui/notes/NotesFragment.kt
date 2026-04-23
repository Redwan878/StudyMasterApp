package com.porashona.studymaster.ui.notes

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.Note
import com.porashona.studymaster.databinding.DialogAddNoteBinding
import com.porashona.studymaster.databinding.FragmentNotesBinding
import com.porashona.studymaster.databinding.ItemNoteBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((requireActivity().application as StudyMasterApplication).extendedRepository)
    }

    private lateinit var adapter: NoteAdapter
    private var activeDialogBinding: DialogAddNoteBinding? = null

    private val favoritesOnly = MutableStateFlow(false)
    private var lastShown: List<Note> = emptyList()

    private val voiceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = data?.get(0) ?: ""
            activeDialogBinding?.etContent?.append(" $spokenText")
        }
    }

    private val exportPdfLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/pdf"),
    ) { uri -> uri?.let { writePdf(it) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteAdapter(
            onClick = { note -> showAddNoteDialog(note) },
            onFavorite = { note -> viewModel.toggleFavorite(note) },
        )
        binding.recyclerNotes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerNotes.adapter = adapter

        binding.fabAddNote.setOnClickListener { showAddNoteDialog() }
        binding.btnExportPdf.setOnClickListener {
            if (lastShown.isEmpty()) {
                Snackbar.make(binding.root, R.string.export_pdf_empty, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            exportPdfLauncher.launch("notes-${System.currentTimeMillis()}.pdf")
        }

        binding.chipGroupNotesFilter.setOnCheckedStateChangeListener { _, checked ->
            favoritesOnly.value = checked.firstOrNull() == R.id.chipNotesFavorites
        }

        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                viewModel.setQuery(s?.toString().orEmpty())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notes.combine(favoritesOnly) { notes, favOnly ->
                    if (favOnly) notes.filter { it.isFavorite } else notes
                }.collect { notes ->
                    lastShown = notes
                    adapter.submitList(notes)
                    binding.tvEmpty.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun writePdf(uri: Uri) {
        val notes = lastShown
        val pdf = PdfDocument()
        val paintTitle = android.graphics.Paint().apply {
            isAntiAlias = true; textSize = 16f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        }
        val paintBody = android.graphics.Paint().apply { isAntiAlias = true; textSize = 12f }
        val pageWidth = 595
        val pageHeight = 842
        val margin = 40
        var pageNumber = 1
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
        var page = pdf.startPage(pageInfo)
        var canvas = page.canvas
        var y = margin.toFloat()

        fun newPage() {
            pdf.finishPage(page)
            pageNumber++
            pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            page = pdf.startPage(pageInfo)
            canvas = page.canvas
            y = margin.toFloat()
        }

        fun drawWrapped(text: String, paint: android.graphics.Paint) {
            val maxWidth = pageWidth - 2 * margin
            val words = text.split(" ")
            var line = StringBuilder()
            for (w in words) {
                val candidate = if (line.isEmpty()) w else "$line $w"
                if (paint.measureText(candidate) > maxWidth) {
                    if (y > pageHeight - margin) newPage()
                    canvas.drawText(line.toString(), margin.toFloat(), y, paint)
                    y += paint.textSize + 4
                    line = StringBuilder(w)
                } else {
                    line = StringBuilder(candidate)
                }
            }
            if (line.isNotEmpty()) {
                if (y > pageHeight - margin) newPage()
                canvas.drawText(line.toString(), margin.toFloat(), y, paint)
                y += paint.textSize + 6
            }
        }

        notes.forEach { n ->
            if (y > pageHeight - margin - 40) newPage()
            drawWrapped(n.title.ifBlank { "(untitled)" }, paintTitle)
            y += 4
            if (n.content.isNotBlank()) drawWrapped(n.content, paintBody)
            y += 10
        }
        pdf.finishPage(page)

        runCatching {
            requireContext().contentResolver.openOutputStream(uri)?.use { out ->
                pdf.writeTo(out)
            }
        }.onSuccess {
            Snackbar.make(binding.root, getString(R.string.export_pdf_done, notes.size), Snackbar.LENGTH_LONG).show()
        }.onFailure {
            Snackbar.make(binding.root, R.string.export_pdf_failed, Snackbar.LENGTH_LONG).show()
        }
        pdf.close()
    }

    private fun showAddNoteDialog(existingNote: Note? = null) {
        activeDialogBinding = DialogAddNoteBinding.inflate(layoutInflater)

        existingNote?.let {
            activeDialogBinding!!.etTitle.setText(it.title)
            activeDialogBinding!!.etContent.setText(it.content)
        }

        activeDialogBinding!!.btnVoice.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "bn-BD")
                putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.listening))
            }
            voiceLauncher.launch(intent)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (existingNote == null) getString(R.string.add_note) else getString(R.string.edit_note))
            .setView(activeDialogBinding!!.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val title = activeDialogBinding!!.etTitle.text.toString()
                val content = activeDialogBinding!!.etContent.text.toString()
                if (title.isNotEmpty()) {
                    val note = existingNote?.copy(title = title, content = content, updatedAt = System.currentTimeMillis())
                        ?: Note(title = title, content = content)
                    viewModel.saveNote(note)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .setNeutralButton(getString(R.string.delete)) { _, _ ->
                existingNote?.let { viewModel.deleteNote(it) }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class NoteAdapter(
    private val onClick: (Note) -> Unit,
    private val onFavorite: (Note) -> Unit,
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content
        holder.binding.btnFavorite.setImageResource(
            if (note.isFavorite) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off,
        )
        holder.binding.btnFavorite.setOnClickListener { onFavorite(note) }
        holder.itemView.setOnClickListener { onClick(note) }
    }

    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
    }
}
