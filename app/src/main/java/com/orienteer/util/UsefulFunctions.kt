package com.orienteer.util

import android.content.res.Resources
import android.util.DisplayMetrics

fun convertDpToPixel(dp: Float): Float {
    return dp * (Resources.getSystem().displayMetrics
        .densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

private fun isTextDifferent(str1: CharSequence?, str2: CharSequence?): Boolean =
    when {
        str1 === str2 -> false
        str1 == null || str2 == null -> true
        str1.length != str2.length -> true
        else ->
            str1.toString() != str2.toString() // Needed in case either string is a Spannable
    }