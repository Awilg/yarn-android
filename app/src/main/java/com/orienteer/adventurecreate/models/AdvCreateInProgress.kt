package com.orienteer.adventurecreate.models

data class AdvCreateInProgress(
    var titleSection: AdvCreateTitleInfoSection
)

data class AdvCreateTitleInfoSection(
    var title : String,
    var description : String
)
