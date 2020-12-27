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

    @EpoxyAttribute lateinit var prompt: String
    @EpoxyAttribute lateinit var hint: String
    @EpoxyAttribute var charCount: Int = 0

    override fun bind(holder: Holder) {
        holder.promptView.text = prompt
        holder.inputView.hint = hint
        holder.inputView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currChar = (charCount - s?.length!!)
                holder.charCountView.text = currChar.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        holder.charCountView.text = charCount.toString()

        holder.inputView.filters = arrayOf(InputFilter.LengthFilter(charCount))
    }
}

class Holder : KotlinEpoxyHolder() {
    val promptView by bind<MaterialTextView>(R.id.create_text_section_prompt)
    val inputView by bind<TextInputEditText>(R.id.create_text_section_input)
    val charCountView by bind<MaterialTextView>(R.id.create_text_section_charcount)
}