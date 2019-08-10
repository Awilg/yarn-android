package com.orienteer.core

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

fun RecyclerView.attachSnapHelperWithListener(
    snapHelper: SnapHelper,
    behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
    onSnapPositionChangeListener: OnSnapPositionChangeListener
) {
    snapHelper.attachToRecyclerView(this)
    val snapOnScrollListener = SnapOnScrollListener(snapHelper, onSnapPositionChangeListener, behavior)
    addOnScrollListener(snapOnScrollListener)
}