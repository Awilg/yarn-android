package com.orienteer

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.maps.model.LatLng
import com.orienteer.core.ClueAdapter
import com.orienteer.models.AdventureInProgress
import com.orienteer.models.ApiStatus
import com.orienteer.models.Clue
import com.orienteer.models.TreasureHunt
import com.orienteer.treasurecreate.AdventureClueCreateAdapter
import com.orienteer.treasurehunts.AdventuresFollowedAdapter
import com.orienteer.treasurehunts.TreasureHuntsAdapter


/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<TreasureHunt>?) {
    val adapter = recyclerView.adapter as TreasureHuntsAdapter
    adapter.submitList(data)
}

@BindingAdapter("adventuresInProgressData")
fun bindAdventuresInProgressRecyclerView(recyclerView: RecyclerView, data: List<AdventureInProgress>?) {
    val adapter = recyclerView.adapter as AdventuresFollowedAdapter
    adapter.submitList(data)
}

@BindingAdapter("cluesData")
fun bindCluesRecyclerView(recyclerView: RecyclerView, data: List<Clue>?) {
    val adapter = recyclerView.adapter as ClueAdapter
    adapter.submitList(data)
}

@BindingAdapter("cluesPreviewsData")
fun bindCluesPreviewRecyclerView(recyclerView: RecyclerView, data: List<Clue>?) {
    val adapter = recyclerView.adapter as AdventureClueCreateAdapter
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

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_connection_error))
            .into(imgView)
    }
}


/**
 * Uses the Glide library to load the image of the map from the static Maps API into an [ImageView]
 */
@BindingAdapter("mapImageFromLatLng", "mapsApiKey")
fun bindImageMapView(imgView: ImageView, latLng: LatLng?, mapsApiKey: String) {
    latLng?.let {
        val path = "maps.googleapis.com/maps/api/staticmap?center=${latLng.latitude},${latLng.longitude}&size=600x300&zoom=15&key=$mapsApiKey"
        val imgUri = path.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .timeout(2000)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_connection_error))
            .into(imgView)
    }
}