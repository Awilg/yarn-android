package com.orienteer.treasurecreate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orienteer.models.Clue

class AdventureClueCreateAdapter : ListAdapter<Clue, ClueCreateViewHolder>(ClueDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClueCreateViewHolder {
        return ClueCreateViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClueCreateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

//        override fun getItemViewType(position: Int): Int {
//        return if (getItem(position).isDark) {
//        R.layout.item_agenda_dark
//        } else {
//        R.layout.item_agenda_light
//        }
//        }
}

class ClueCreateViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(clue: Clue) {
        binding.executePendingBindings()
    }
}

/**
 * Allows the RecyclerView to determine which items have changed when the [List] of [Clue]
 * has been updated.
 */
object ClueDiffCallback : DiffUtil.ItemCallback<Clue>() {
    override fun areItemsTheSame(oldItem: Clue, newItem: Clue): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Clue, newItem: Clue): Boolean {
        return oldItem.id == newItem.id
    }
}