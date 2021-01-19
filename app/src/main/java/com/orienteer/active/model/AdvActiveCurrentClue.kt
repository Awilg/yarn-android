package com.orienteer.active.model

import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.orienteer.R
import com.orienteer.models.Clue
import com.orienteer.models.ClueType
import com.orienteer.util.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.e_advactive_current_clue)
abstract class AdvActiveCurrentClue : EpoxyModelWithHolder<AdvActiveCurrentClue.Holder>() {

    @EpoxyAttribute
    var clue: Clue? = null

    override fun bind(holder: Holder) {
        clue?.let {
            holder.cluePromptView.text = it.prompt
            when (it.type) {
                ClueType.Text -> {
                    holder.clueIconView.setBackgroundResource(R.drawable.ic_icons8_edit_50)
                    holder.actionButton.setText(R.string.clue_current_action_text)
                }
                ClueType.Photo -> {
                    holder.clueIconView.setBackgroundResource(R.drawable.ic_outline_camera_alt_24)
                    holder.actionButton.setText(R.string.clue_current_action_photo)
                }
                ClueType.Location -> {
                    holder.clueIconView.setBackgroundResource(R.drawable.ic_icons8_marker)
                    holder.actionButton.setText(R.string.clue_current_action_location)
                }
                else -> {
                    holder.clueIconView.setBackgroundResource(R.drawable.ic_outline_bug_report_24)
                    holder.actionButton.setText(R.string.clue_current_action_bug)
                }
            }
        }
    }

    override fun unbind(holder: Holder) {}

    class Holder : KotlinEpoxyHolder() {
        val cluePromptView by bind<MaterialTextView>(R.id.clue_prompt)
        val clueIconView by bind<ImageView>(R.id.clue_icon)
        val actionButton by bind<MaterialButton>(R.id.clue_action_button)

    }
}
