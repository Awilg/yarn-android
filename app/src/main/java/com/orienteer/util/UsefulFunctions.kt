package com.orienteer.util

import android.content.res.Resources
import android.util.DisplayMetrics

fun convertDpToPixel(dp: Float): Float {
    return dp * (Resources.getSystem().displayMetrics
        .densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}