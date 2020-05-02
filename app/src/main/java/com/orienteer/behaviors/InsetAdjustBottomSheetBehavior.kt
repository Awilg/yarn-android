package com.orienteer.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior

class InsetAdjustBottomSheetBehavior : BottomSheetBehavior<View> {
    constructor() : super()
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return  axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }


    /**
     * @param coordinatorLayout the CoordinatorLayout parent of the view this Behavior is
     *                          associated with
     * @param child the child view of the CoordinatorLayout this Behavior is associated with
     * @param target the descendant view of the CoordinatorLayout performing the nested scroll
     * @param dxConsumed horizontal pixels consumed by the target's own scrolling operation
     * @param dyConsumed vertical pixels consumed by the target's own scrolling operation
     * @param dxUnconsumed horizontal pixels not consumed by the target's own scrolling
     *                     operation, but requested by the user
     * @param dyUnconsumed vertical pixels not consumed by the target's own scrolling operation,
     *                     but requested by the user
     * @param type the type of input which cause this scroll event
     * @param consumed output. Upon this method returning, should contain the scroll
     *                 distances consumed by this Behavior
     */
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        var x = 6
    }
}