package com.orienteer.adventurecreate.ext

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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

fun Fragment.getUrisFromResultData(resultData: Intent?): MutableList<Uri> {
    val selectedUris: MutableList<Uri> = arrayListOf()
    if (resultData?.clipData != null) {
        resultData.clipData?.let { uris ->
            activity?.let {
                for (index in 0 until uris.itemCount) {
                    selectedUris.add(uris.getItemAt(index).uri)
                }
            }
        }
    } else {
        resultData?.data?.let { uri ->
            selectedUris.add(uri)
        }
    }
    return selectedUris
}

fun Fragment.getBitmapsFromResultData(resultData: Intent?): MutableList<Bitmap> {
    val selectedUris: MutableList<Uri> = this.getUrisFromResultData(resultData)
    val decodedBitmaps: MutableList<Bitmap> = arrayListOf()
    selectedUris.forEach { uri ->
        decodedBitmaps.add(
            BitmapFactory.decodeStream(
                activity?.contentResolver?.openInputStream(
                    uri
                )
            )
        )
    }
    return decodedBitmaps
}
