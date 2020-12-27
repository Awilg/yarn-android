package com.orienteer.adventurecreate.models

data class AdvCreateInProgress(
    var titleSection: AdvCreateTitleInfoSection = AdvCreateTitleInfoSection()
)

data class AdvCreateTitleInfoSection(
    var title : String = "initial_title",
    var description : String = "initial_description"
)
