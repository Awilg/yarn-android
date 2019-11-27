package com.orienteer.treasurehunts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orienteer.databinding.CardAdventureFeaturedBinding
import com.orienteer.databinding.CardTreasureHuntBinding
import com.orienteer.models.TreasureHunt

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the click listener to use on each item
 */
class TreasureHuntsAdapter(
    private val onClickListener: OnClickListener,
    private val useFeaturedBinding: Boolean
) :
    ListAdapter<TreasureHunt, RecyclerView.ViewHolder>(DiffCallback) {
    /**
     * The TreasureHuntViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [TreasureHunt] information.
     */
    class TreasureHuntViewHolder(private var binding: CardTreasureHuntBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hunt: TreasureHunt) {
            binding.treasureHunt = hunt
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    class FeaturedAdventureViewHolder(private var binding: CardAdventureFeaturedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hunt: TreasureHunt) {
            binding.treasureHunt = hunt
            binding.executePendingBindings()
        }
    }


    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [TreasureHunt]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<TreasureHunt>() {
        override fun areItemsTheSame(oldItem: TreasureHunt, newItem: TreasureHunt): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TreasureHunt, newItem: TreasureHunt): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (useFeaturedBinding) {
            return FeaturedAdventureViewHolder(
                CardAdventureFeaturedBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
        return TreasureHuntViewHolder(CardTreasureHuntBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val treasureHunt = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(treasureHunt)
        }
        if (useFeaturedBinding) {
            (holder as FeaturedAdventureViewHolder).bind(treasureHunt)
        } else {
            (holder as TreasureHuntViewHolder).bind(treasureHunt)
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [TreasureHunt]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [TreasureHunt]
     */
    class OnClickListener(val clickListener: (treasureHunt: TreasureHunt) -> Unit) {
        fun onClick(treasureHunt: TreasureHunt) = clickListener(treasureHunt)
    }

    /**
     * Provides a get method to retrieve the data object for a current position in the
     * list of [TreasureHunt] used by the [RecyclerView]
     * @param position the position in the [RecyclerView]
     */
    public override fun getItem(position: Int): TreasureHunt {
        return super.getItem(position)
    }
}
