package com.orienteer.adventurecreate.ext

import android.content.Intent
import androidx.fragment.app.Fragment
import com.orienteer.adventurecreate.fragments.IMAGE_MIME_TYPE

// Request code for selecting a PDF document.
const val IMAGE_PICKER_REQUEST_CODE = 2

fun Fragment.openImageSaf() {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = IMAGE_MIME_TYPE
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    }

    startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE)
}