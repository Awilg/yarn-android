package com.orienteer.adventurecreate.models

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.orienteer.R
import com.orienteer.util.SimpleTextWatcher
import com.orienteer.util.setTextIfDifferent

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class AdvCreateInputRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val editText by lazy { findViewById<TextInputEditText>(R.id.create_text_section_input) }
    private val img by lazy { findViewById<ImageView>(R.id.create_text_section_input) }
    private val promptText by lazy { findViewById<MaterialTextView>(R.id.create_text_section_prompt) }
    private val editTextWatcher = SimpleTextWatcher { onEditTextChanged?.invoke(it) }

    @set:TextProp
    var text: CharSequence? = null
    @set:TextProp
    var hint: CharSequence? = null
    @set:TextProp
    var prompt: CharSequence? = null

    @set:CallbackProp
    var onEditTextChanged: ((newText: String) -> Unit)? = null

    init {
        inflate(context, R.layout.layout_e_create_text_section, this)
    }

    @AfterPropsSet
    fun postBindSetup() {
        editText.also {
            it.setTextIfDifferent(text)
            it.hint = hint
            it.removeTextChangedListener(editTextWatcher)
            it.addTextChangedListener(editTextWatcher)
        }

        promptText.also {
            it.text = prompt
        }
    }

    @OnViewRecycled
    fun onViewRecycled() {
        editText.removeTextChangedListener(editTextWatcher)
    }

}