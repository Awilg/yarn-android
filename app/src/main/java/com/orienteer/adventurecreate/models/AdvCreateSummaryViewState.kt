package com.orienteer.adventurecreate.models

data class AdvCreateSummaryViewState(
    val sectionItems: List<SectionItem>
)

typealias OnClickHandler = () -> Unit