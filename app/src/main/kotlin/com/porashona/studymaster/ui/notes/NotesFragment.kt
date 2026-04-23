package com.porashona.studymaster.ui.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.Note
import com.porashona.studymaster.databinding.DialogAddNoteBinding
import com.porashona.studymaster.databinding.FragmentNotesBinding
import com.porashona.studymaster.databinding.ItemNoteBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((requireActivity().application as StudyMasterApplication).extendedRepository)
    }

    private lateinit var adapter: NoteAdapter
    private var activeDialogBinding: DialogAddNoteBinding? = null

    private val voiceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = data?.get(0) ?: ""
            activeDialogBinding?.etContent?.append(" $spokenText")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteAdapter { note -> showAddNoteDialog(note) }
        binding.recyclerNotes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerNotes.adapter = adapter

        binding.fabAddNote.setOnClickListener { showAddNoteDialog() }

        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                viewModel.setQuery(s?.toString().orEmpty())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notes.collectLatest { notes ->
                adapter.submitList(notes)
                binding.tvEmpty.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
            }
        }
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
}

// Added the missing NoteAdapter class directly here to fix the Unresolved Reference
class NoteAdapter(private val onClick: (Note) -> Unit) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content
        holder.itemView.setOnClickListener { onClick(note) }
    }

    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
    }
}