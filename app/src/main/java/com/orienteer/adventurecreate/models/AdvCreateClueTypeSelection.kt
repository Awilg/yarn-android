package com.orienteer.adventurecreate.models

import android.widget.RadioGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.orienteer.R
import com.orienteer.models.ClueType
import com.orienteer.util.KotlinEpoxyHolder


@EpoxyModelClass(layout = R.layout.e_create_clue_type_selection)
abstract class AdvCreateClueTypeSelection :
    EpoxyModelWithHolder<AdvCreateClueTypeSelection.Holder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClueTypeUpdated: ((clueType: ClueType) -> Unit)? = null

    private val checkedChangedListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
        when (checkedId) {
            R.id.clue_text_radio_btn -> onClueTypeUpdated?.invoke(ClueType.Text)
            R.id.clue_photo_radio_btn -> onClueTypeUpdated?.invoke(ClueType.Photo)
            R.id.clue_location_radio_btn -> onClueTypeUpdated?.invoke(ClueType.Location)
        }
    }

    override fun bind(holder: Holder) {
        holder.clueTypeRadioGroup.setOnCheckedChangeListener(checkedChangedListener)
    }

    override fun unbind(holder: Holder) {
        holder.clueTypeRadioGroup.setOnCheckedChangeListener(null)
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }

    class Holder : KotlinEpoxyHolder() {
        val clueTypeRadioGroup by bind<RadioGroup>(R.id.clue_type_radio_group)
    }
}
