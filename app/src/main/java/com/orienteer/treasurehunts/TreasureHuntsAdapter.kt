package com.orienteer.treasurehunts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orienteer.databinding.CardAdventureFeaturedBinding
import com.orienteer.databinding.CardBottomlessAdventureBinding
import com.orienteer.databinding.CardTreasureHuntBinding
import com.orienteer.models.Adventure


class TreasureHuntsAdapter(
    private val onClickListener: OnClickListener,
    private val viewHolder: AdventureViewHolder
) :
	ListAdapter<Adventure, RecyclerView.ViewHolder>(DiffCallback) {

    enum class AdventureViewHolder {
        Normal, Featured, Bottomless
    }

    class TreasureHuntViewHolder(private var binding: CardTreasureHuntBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(hunt: Adventure) {
            binding.treasureHunt = hunt
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    class FeaturedAdventureViewHolder(private var binding: CardAdventureFeaturedBinding) :
        RecyclerView.ViewHolder(binding.root) {
		fun bind(hunt: Adventure) {
            binding.treasureHunt = hunt
            binding.executePendingBindings()
        }
    }

    class BottomlessAdventureViewHolder(private var binding: CardBottomlessAdventureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hunt: Adventure) {
            binding.featuredAdventure = hunt
            binding.executePendingBindings()
        }
    }

	companion object DiffCallback : DiffUtil.ItemCallback<Adventure>() {
		override fun areItemsTheSame(oldItem: Adventure, newItem: Adventure): Boolean {
            return oldItem === newItem
        }

		override fun areContentsTheSame(oldItem: Adventure, newItem: Adventure): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewHolder) {
            AdventureViewHolder.Bottomless -> return BottomlessAdventureViewHolder(
                CardBottomlessAdventureBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            AdventureViewHolder.Featured ->
                return FeaturedAdventureViewHolder(
                    CardAdventureFeaturedBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            AdventureViewHolder.Normal ->  return TreasureHuntViewHolder(CardTreasureHuntBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val treasureHunt = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(treasureHunt)
        }
        when(viewHolder){
            AdventureViewHolder.Normal ->  (holder as TreasureHuntViewHolder).bind(treasureHunt)
            AdventureViewHolder.Featured -> (holder as FeaturedAdventureViewHolder).bind(treasureHunt)
            AdventureViewHolder.Bottomless -> (holder as BottomlessAdventureViewHolder).bind(treasureHunt)
        }
    }


	class OnClickListener(val clickListener: (adventure: Adventure) -> Unit) {
		fun onClick(adventure: Adventure) = clickListener(adventure)
    }

	public override fun getItem(position: Int): Adventure {
        return super.getItem(position)
    }
}
