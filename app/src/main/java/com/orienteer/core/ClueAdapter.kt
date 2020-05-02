package com.orienteer.core

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orienteer.R
import com.orienteer.databinding.ClueItemBinding
import com.orienteer.models.Clue
import com.orienteer.models.ClueState
import com.orienteer.models.ClueType

class ClueAdapter(
    private val context: Context,
    private val clueAdapterListener: ClueAdapterListener
) :
    ListAdapter<Clue, ClueAdapter.ClueViewHolder>(DiffCallback) {

    /**
     * The ClueViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Clue] information.
     */
    class ClueViewHolder(
        private var context: Context,
        private var binding: ClueItemBinding,
        private val clueAdapterListener: ClueAdapterListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clue: Clue) {
            binding.clue = clue

            when (clue.state) {
                ClueState.ACTIVE -> {
                    binding.cluePromptText.text = clue.prompt
                }
                ClueState.COMPLETED -> {
                    binding.cluePromptText.text = clue.prompt
                }
                ClueState.LOCKED -> {
                    binding.cluePromptText.text = context.getString(R.string.clue_prompt_locked)
                }
            }

            binding.clueTypeImage.setOnClickListener { clueAdapterListener.clueTypeOnClick(clue.type) }
            binding.clueHintImage.setOnClickListener { clueAdapterListener.clueHintOnClick(clue.hint) }
            binding.clueSolveImage.setOnClickListener { clueAdapterListener.clueSolveOnClick(clue) }

            when (clue.type) {
                ClueType.Location -> {
                    binding.clueTypeImage.setImageDrawable(context.getDrawable(R.drawable.ic_location_on_black_24dp))
                }
                ClueType.Treasure -> TODO()
                ClueType.Photo -> {
                    binding.clueTypeImage.setImageDrawable(context.getDrawable(R.drawable.ic_photo_camera_black_24dp))
                }
                ClueType.Text -> {
                    binding.clueTypeImage.setImageDrawable(context.getDrawable(R.drawable.ic_text_format_black_24dp))
                }
            }

            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClueViewHolder {
        return ClueViewHolder(
            context,
            ClueItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clueAdapterListener
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ClueViewHolder, position: Int) {
        val clue = getItem(position)

        holder.bind(clue)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Clue]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Clue>() {
        override fun areItemsTheSame(oldItem: Clue, newItem: Clue): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Clue, newItem: Clue): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Provides a get method to retrieve the data object for a current position in the
     * list of [Clue] used by the [RecyclerView]
     * @param position the position in the [RecyclerView]
     */
    public override fun getItem(position: Int): Clue {
        return super.getItem(position)
    }
}