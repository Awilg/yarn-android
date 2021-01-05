package com.orienteer.adventurecreate.models

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.orienteer.R
import com.orienteer.util.KotlinEpoxyHolder


@EpoxyModelClass(layout = R.layout.layout_e_create_text_section)
abstract class AdvCreateTextSection : EpoxyModelWithHolder<Holder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) lateinit var textWatcher: TextWatcher
    @EpoxyAttribute lateinit var value: CharSequence
    @EpoxyAttribute lateinit var prompt: String
    @EpoxyAttribute lateinit var hint: String
    @EpoxyAttribute var charCount: Int = 0

    override fun bind(holder: Holder) {
        holder.promptView.text = prompt
        holder.inputView.hint = hint
        holder.inputView.setText(value)
        holder.inputView.setSelection(value.length)
        holder.inputView.addTextChangedListener(textWatcher)
        holder.charCountView.text = charCount.toString()
        holder.inputView.filters = arrayOf(InputFilter.LengthFilter(charCount))
    }

    override fun unbind(holder: Holder) {
        holder.inputView.removeTextChangedListener(textWatcher)
    }

}

class Holder : KotlinEpoxyHolder() {
    val promptView by bind<MaterialTextView>(R.id.create_text_section_prompt)
    val inputView by bind<TextInputEditText>(R.id.create_text_section_input)
    val charCountView by bind<MaterialTextView>(R.id.create_text_section_charcount)
}