package com.orienteer

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Api
import com.orienteer.core.ClueAdapter
import com.orienteer.models.ApiStatus
import com.orienteer.models.Clue
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


@BindingAdapter("cluesData")
fun bindCluesRecyclerView(recyclerView: RecyclerView, data: List<Clue>?) {
    val adapter = recyclerView.adapter as ClueAdapter
    adapter.submitList(data)
}

/**
 * This binding adapter displays the [ApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
