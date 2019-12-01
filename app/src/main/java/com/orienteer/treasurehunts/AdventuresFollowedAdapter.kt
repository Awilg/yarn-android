package com.orienteer.treasurehunts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orienteer.databinding.CardAdventureInProgressBinding
import com.orienteer.models.Adventure
import com.orienteer.models.AdventureInProgress

class AdventuresFollowedAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<AdventureInProgress, AdventuresFollowedAdapter.AdventuresFollowedViewHolder>(
        DiffCallback
    ) {

    /**
     * The TreasureHuntViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Adventure] information.
     */
    class AdventuresFollowedViewHolder(private var binding: CardAdventureInProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adventure: AdventureInProgress) {
            binding.adventureInProgress = adventure
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdventuresFollowedViewHolder {
        return AdventuresFollowedViewHolder(
            CardAdventureInProgressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: AdventuresFollowedViewHolder, position: Int) {
        val adventureInProgress = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(adventureInProgress)
        }
        holder.bind(adventureInProgress)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [AdventureInProgress]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [AdventureInProgress]
     */
    class OnClickListener(val clickListener: (adventure: AdventureInProgress) -> Unit) {
        fun onClick(adventure: AdventureInProgress) = clickListener(adventure)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [AdventureInProgress]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<AdventureInProgress>() {
        override fun areItemsTheSame(
            oldItem: AdventureInProgress,
            newItem: AdventureInProgress
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: AdventureInProgress,
            newItem: AdventureInProgress
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}