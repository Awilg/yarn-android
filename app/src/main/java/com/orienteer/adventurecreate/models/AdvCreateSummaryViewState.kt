package com.orienteer.adventurecreate.models

data class AdvCreateSummaryViewState(
    val sectionItems: List<SectionItem>
)

typealias OnClickHandler = () -> Unit

data class SectionItem(
    val name: String,
    val completed: Boolean,
    val continuable: Boolean,
    val onClickHandler: OnClickHandler
)
