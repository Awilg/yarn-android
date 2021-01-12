package com.orienteer.adventurecreate.models

import android.widget.CompoundButton
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import com.orienteer.R
import com.orienteer.util.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.e_create_toggle)
abstract class AdvCreateToggleItem : EpoxyModelWithHolder<AdvCreateToggleItem.Holder>() {

    private val listener =
        CompoundButton.OnCheckedChangeListener { _, isChecked -> onToggleItem?.invoke(isChecked) }

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onToggleItem: ((enabled: Boolean) -> Unit)? = null

    @EpoxyAttribute
    var text: CharSequence? = null

    @EpoxyAttribute
    var enabled: Boolean? = null

    override fun bind(holder: Holder) {
        text?.let { holder.text.text = it }
        holder.toggle.setOnCheckedChangeListener(listener)
        enabled?.let { holder.toggle.isChecked = it }
    }

    override fun unbind(holder: Holder) {
        holder.toggle.setOnCheckedChangeListener(null)
    }

    class Holder : KotlinEpoxyHolder() {
        val toggle by bind<SwitchMaterial>(R.id.toggle)
        val text by bind<MaterialTextView>(R.id.toggle_prompt)
    }
}