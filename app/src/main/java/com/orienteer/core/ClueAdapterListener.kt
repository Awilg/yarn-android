package com.orienteer.core

import com.orienteer.models.Clue
import com.orienteer.models.ClueType

interface ClueAdapterListener {
    fun clueTypeOnClick(type : ClueType)
    fun clueSolveOnClick(clue: Clue)
    fun clueHintOnClick(hint : String)
}