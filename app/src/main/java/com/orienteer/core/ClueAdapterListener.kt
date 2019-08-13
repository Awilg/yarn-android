package com.orienteer.core

import com.orienteer.models.ClueType

interface ClueAdapterListener {
    fun clueTypeOnClick(type : ClueType)
    fun clueSolveOnClick(type: ClueType)
    fun clueHintOnClick(hint : String)
}