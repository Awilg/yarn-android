package com.orienteer.detail.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.orienteer.R
import com.orienteer.bindImage
import com.orienteer.util.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.e_advdetail_clue_summary)
abstract class AdvDetailClueSummary : EpoxyModelWithHolder<AdvDetailClueSummary.Holder>() {

    @EpoxyAttribute
    var numTextClues: Int = 0

    @EpoxyAttribute
    var numPhotoClues: Int = 0

    @EpoxyAttribute
    var numLocationClues: Int = 0

    @EpoxyAttribute
    var numTreasure: Int = 0

    @EpoxyAttribute
    var numCashPrize: Int = 0

    @EpoxyAttribute
    var authorImage: String? = null

    override fun bind(holder: Holder) {
        holder.textClueView.text = numTextClues.toString()
        holder.photoClueView.text = numPhotoClues.toString()
        holder.locationClueView.text = numLocationClues.toString()
        holder.treasureAmount.text = numTreasure.toString()
        holder.prizeAmount.text = numCashPrize.toString()
        authorImage?.let {
            bindImage(holder.authorImage, it)
        }
    }

    override fun unbind(holder: Holder) {
    }

    class Holder : KotlinEpoxyHolder() {
        val textClueView by bind<MaterialTextView>(R.id.clue_text_number)
        val photoClueView by bind<MaterialTextView>(R.id.clue_photo_number)
        val locationClueView by bind<MaterialTextView>(R.id.clue_location_number)
        val treasureAmount by bind<MaterialTextView>(R.id.treasure_amount)
        val prizeAmount by bind<MaterialTextView>(R.id.prize_amount)

        val authorImage by bind<ShapeableImageView>(R.id.author_image)
    }
}
