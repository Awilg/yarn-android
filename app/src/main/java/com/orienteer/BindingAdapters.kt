package com.orienteer

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orienteer.models.TreasureHunt
import com.orienteer.treasurehunts.TreasureHuntsAdapter


/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<TreasureHunt>?) {
    val adapter = recyclerView.adapter as TreasureHuntsAdapter
    adapter.submitList(data)
}