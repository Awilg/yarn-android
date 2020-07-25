package com.orienteer.map

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val startMargin: Int, private val verticalMargin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                left = startMargin
            }

            // Need to use the adapter count because the recyclerview only uses 4 views total
            if (parent.getChildAdapterPosition(view) < parent.adapter!!.itemCount - 1) {
                right = (startMargin / 2)
            } else {
                right = startMargin
            }

            top = verticalMargin
            bottom = verticalMargin
        }
    }
}