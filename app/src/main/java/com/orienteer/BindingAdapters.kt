package com.orienteer

import android.view.View
import android.view.WindowInsets
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.LatLng
import com.orienteer.core.ClueAdapter
import com.orienteer.models.Adventure
import com.orienteer.models.AdventureInProgress
import com.orienteer.models.ApiStatus
import com.orienteer.models.Clue
import com.orienteer.treasurehuntdetail.AdventureDetailImagePagerAdapter
import com.orienteer.treasurehunts.AdventuresFollowedAdapter
import com.orienteer.treasurehunts.TreasureHuntsAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Adventure>?) {
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

@BindingAdapter("createAdapterForGallery")
fun updateGalleryAdapter(viewPager2: ViewPager2, imageUrls: List<String>) {

    val adapter = viewPager2.adapter as AdventureDetailImagePagerAdapter
    adapter.updateGalleryImages(imageUrls)
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
    imgView.elevation = 0f
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


// Handling insets
data class InitialPadding(
    val left: Int, val top: Int,
    val right: Int, val bottom: Int
)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)


fun View.doOnApplyWindowInsets(f: (View, WindowInsets, InitialPadding) -> Unit) {
    // Create a snapshot of the view's padding state
    val initialPadding = recordInitialPaddingForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding state
    setOnApplyWindowInsetsListener { v, insets ->
        f(v, insets, initialPadding)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}


fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

@BindingAdapter(
    "paddingLeftSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingRightSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    requireAll = false
)
fun applySystemWindows(
    view: View,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    view.doOnApplyWindowInsets { v, insets, padding ->
        val left = if (applyLeft) insets.systemWindowInsetLeft else 0
        val top = if (applyTop) insets.systemWindowInsetTop else 0
        val right = if (applyRight) insets.systemWindowInsetRight else 0
        val bottom = if (applyBottom) insets.systemWindowInsetBottom else 0

        v.setPadding(
            padding.left + left,
            padding.top + top,
            padding.right + right,
            padding.bottom + bottom
        )
    }
}