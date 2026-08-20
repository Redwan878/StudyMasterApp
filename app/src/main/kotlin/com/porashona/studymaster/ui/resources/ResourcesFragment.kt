/*
package com.porashona.studymaster.ui.resources

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope // ADDED IMPORT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.ResourceType
import com.porashona.studymaster.data.model.StudyResource
import com.porashona.studymaster.data.repository.ExtendedRepository
import com.porashona.studymaster.databinding.DialogAddResourceBinding
import com.porashona.studymaster.databinding.FragmentResourcesBinding
import com.porashona.studymaster.databinding.ItemResourceBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ResourcesFragment : Fragment() {
    private var _binding: FragmentResourcesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResourcesViewModel by viewModels {
        ResourcesViewModelFactory((requireActivity().application as StudyMasterApplication).extendedRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentResourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ResourceAdapter { resource ->
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(resource.url)))
                viewModel.visitResource(resource)
            } catch (e: Exception) { e.printStackTrace() }
        }
        binding.recyclerResources.layoutManager = LinearLayoutManager(context)
        binding.recyclerResources.adapter = adapter

        binding.fabAdd.setOnClickListener { showAddDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resources.collectLatest { adapter.submitList(it) }
        }
    }

    private fun showAddDialog() {
        val dBinding = DialogAddResourceBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.add_resource))
            .setView(dBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val title = dBinding.etTitle.text.toString()
                val url = dBinding.etUrl.text.toString()
                if (title.isNotEmpty() && url.isNotEmpty()) {
                    viewModel.addResource(StudyResource(title = title, url = url, type = ResourceType.WEBSITE))
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}

class ResourcesViewModel(private val repository: ExtendedRepository) : ViewModel() {
    val resources = repository.allResources

    fun addResource(res: StudyResource) = viewModelScope.launch {
        repository.insertResource(res)
    }

    fun visitResource(res: StudyResource) = viewModelScope.launch {
        repository.visitResource(res.id)
    }
}

class ResourcesViewModelFactory(private val repo: ExtendedRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ResourcesViewModel(repo) as T
}

class ResourceAdapter(private val onClick: (StudyResource) -> Unit) : androidx.recyclerview.widget.ListAdapter<StudyResource, ResourceAdapter.VH>(DiffCallback()) {
    class VH(val binding: ItemResourceBinding) : RecyclerView.ViewHolder(binding.root)
    class DiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<StudyResource>() {
        override fun areItemsTheSame(old: StudyResource, new: StudyResource) = old.id == new.id
        override fun areContentsTheSame(old: StudyResource, new: StudyResource) = old == new
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(ItemResourceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.tvTitle.text = item.title
        holder.binding.tvType.text = item.type.name
        holder.itemView.setOnClickListener { onClick(item) }
    }
}
*/