package com.orienteer.util

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.widget.EditText

fun convertDpToPixel(dp: Float): Float {
    return dp * (Resources.getSystem().displayMetrics
        .densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun EditText.setTextIfDifferent(newText: CharSequence?): Boolean {
    if (!isTextDifferent(newText, text)) {
        // Previous text is the same. No op
        return false
    }

    setText(newText)
    // Since the text changed we move the cursor to the end of the new text.
    // This allows us to fill in text programmatically with a different value,
    // but if the user is typing and the view is rebound we won't lose their cursor position.
    setSelection(newText?.length ?: 0)
    return true
}

private fun isTextDifferent(str1: CharSequence?, str2: CharSequence?): Boolean =
    when {
        str1 === str2 -> false
        str1 == null || str2 == null -> true
        str1.length != str2.length -> true
        else ->
            str1.toString() != str2.toString() // Needed in case either string is a Spannable
    }


class SimpleTextWatcher(val onTextChanged: (newText: String) -> Unit) : TextWatcher {
    override fun afterTextChanged(s: Editable) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onTextChanged(s.toString())
    }
}