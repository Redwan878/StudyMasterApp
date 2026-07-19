package com.porashona.studymaster.ui.quotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.porashona.studymaster.R
import com.porashona.studymaster.data.model.Quote
import com.porashona.studymaster.databinding.ItemQuoteBinding

class QuoteAdapter(
    private val onFavorite: (Quote) -> Unit,
    private val onShare: (Quote) -> Unit,
    private val onDelete: (Quote) -> Unit,
) : ListAdapter<Quote, QuoteAdapter.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false,
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(q: Quote) {
            binding.tvQuoteBn.text = "\u201C${q.textBn}\u201D"
            binding.tvQuoteEn.text = if (q.textEn.isNotBlank()) "\u201C${q.textEn}\u201D" else ""
            binding.tvQuoteEn.visibility =
                if (q.textEn.isNotBlank()) android.view.View.VISIBLE else android.view.View.GONE
            val author = q.authorBn.ifBlank { q.author }
            binding.tvAuthor.text = if (author.isNotBlank()) "— $author" else ""
            binding.tvAuthor.visibility =
                if (author.isNotBlank()) android.view.View.VISIBLE else android.view.View.GONE

            binding.btnFavorite.setIconResource(
                if (q.isFavorite) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )
            binding.btnFavorite.setOnClickListener { onFavorite(q) }
            binding.btnShare.setOnClickListener { onShare(q) }
            binding.btnDelete.visibility =
                if (q.isCustom) android.view.View.VISIBLE else android.view.View.GONE
            binding.btnDelete.setOnClickListener { onDelete(q) }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(a: Quote, b: Quote) = a.id == b.id
            override fun areContentsTheSame(a: Quote, b: Quote) = a == b
        }
    }
}
