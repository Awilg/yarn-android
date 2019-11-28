package com.orienteer.treasurecreate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orienteer.databinding.ClueItemPreviewBinding
import com.orienteer.models.BaseClue
import com.orienteer.models.Clue

class AdventureClueCreateAdapter :
    ListAdapter<BaseClue, RecyclerView.ViewHolder>(ClueDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClueCreateViewHolder {
        return ClueCreateViewHolder(
            ClueItemPreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val clue = getItem(position)
        (holder as ClueCreateViewHolder).bind(clue)
    }

    class ClueCreateViewHolder(
        private val binding: ClueItemPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clue: BaseClue) {
            binding.clue = clue
            binding.executePendingBindings()
        }
    }
}

/**
 * Allows the RecyclerView to determine which items have changed when the [List] of [Clue]
 * has been updated.
 */
object ClueDiffCallback : DiffUtil.ItemCallback<BaseClue>() {
    override fun areItemsTheSame(oldItem: BaseClue, newItem: BaseClue): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: BaseClue, newItem: BaseClue): Boolean {
        return oldItem.answer == newItem.answer &&
                oldItem.cluePrompt == newItem.cluePrompt &&
                oldItem.type == newItem.type
    }
}