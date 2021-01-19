package com.orienteer.active.model

import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textview.MaterialTextView
import com.orienteer.R
import com.orienteer.models.Clue
import com.orienteer.models.ClueType
import com.orienteer.util.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.e_advactive_clue_item)
abstract class AdvActiveCompletedClue : EpoxyModelWithHolder<AdvActiveCompletedClue.Holder>() {

    @EpoxyAttribute
    var clue: Clue? = null

    override fun bind(holder: Holder) {
        clue?.let {
            holder.cluePromptView.text = it.prompt
            when (it.type) {
                ClueType.Text -> holder.clueIconView.setBackgroundResource(R.drawable.ic_icons8_edit_50)
                ClueType.Photo -> holder.clueIconView.setBackgroundResource(R.drawable.ic_outline_camera_alt_24)
                ClueType.Location -> holder.clueIconView.setBackgroundResource(R.drawable.ic_icons8_marker)
                else -> holder.clueIconView.setBackgroundResource(R.drawable.ic_outline_bug_report_24)
            }
        }
    }

    override fun unbind(holder: Holder) {}

    class Holder : KotlinEpoxyHolder() {
        val cluePromptView by bind<MaterialTextView>(R.id.clue_prompt)
        val clueIconView by bind<ImageView>(R.id.clue_icon)
    }
}
