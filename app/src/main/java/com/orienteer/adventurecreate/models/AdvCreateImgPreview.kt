package com.orienteer.adventurecreate.models

import android.graphics.Bitmap
import android.view.ViewParent
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.orienteer.R
import com.orienteer.util.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.e_img_preview)
abstract class AdvCreateImgPreview : EpoxyModelWithHolder<ImageHolder>() {

    @EpoxyAttribute
    lateinit var bitmap: Bitmap

    @EpoxyAttribute
    var preloading: Boolean = false

    override fun bind(holder: ImageHolder) {
        holder.image.setImageBitmap(bitmap)
    }

    override fun unbind(holder: ImageHolder) {
        holder.image.setImageBitmap(null)
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return 1
    }
}

class ImageHolder(parent: ViewParent) : KotlinEpoxyHolder() {
    val image by bind<ImageView>(R.id.img_preview)
}