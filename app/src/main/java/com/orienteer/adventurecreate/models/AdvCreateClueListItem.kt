package com.orienteer.adventurecreate.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.orienteer.R
import com.orienteer.models.ClueType
import com.orienteer.util.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.e_advcreate_clue_list_item)
abstract class AdvCreateClueListItem : EpoxyModelWithHolder<AdvCreateClueListItem.Holder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClueTypeUpdated: ((clueType: ClueType) -> Unit)? = null

    @EpoxyAttribute
    var prompt: CharSequence? = null

    @EpoxyAttribute
    var clueType: ClueType? = null

    override fun bind(holder: Holder) {
        prompt?.let { holder.cluePrompt.text = it }
        when (clueType) {
            ClueType.Text -> holder.clueIcon.setBackgroundResource(R.drawable.ic_icons8_edit_50)
            ClueType.Photo -> holder.clueIcon.setBackgroundResource(R.drawable.ic_outline_camera_alt_24)
            ClueType.Location -> holder.clueIcon.setBackgroundResource(R.drawable.ic_icons8_marker)
            else -> holder.clueIcon.setBackgroundResource(R.drawable.ic_outline_bug_report_24)
        }
    }

    override fun unbind(holder: Holder) {
    }

    class Holder : KotlinEpoxyHolder() {
        val clueIcon by bind<ShapeableImageView>(R.id.clue_icon)
        val cluePrompt by bind<MaterialTextView>(R.id.clue_prompt)
    }
}
