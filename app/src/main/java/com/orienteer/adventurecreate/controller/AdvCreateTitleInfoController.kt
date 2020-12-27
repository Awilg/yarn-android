package com.orienteer.adventurecreate.controller

import com.airbnb.epoxy.EpoxyController
import com.orienteer.adventurecreate.models.advCreateTextSection
import com.orienteer.createSectionHeader
import com.orienteer.createTextSection

class AdvCreateTitleInfoController : EpoxyController() {
    override fun buildModels() {
        createSectionHeader {
            id(hashCode())
            header("Name your adventure")
            subtitle("Be descriptive. Try to include what makes this adventure unique.")
        }

        advCreateTextSection {
            id(hashCode())
            prompt("Title")
            hint("e.g. The Great Orange Monster")
            charCount(50)
        }

        advCreateTextSection {
            id(hashCode())
            prompt("Description")
            hint("e.g. This adventure will take you across San Francisco on the hunt for the great Orange Monster, weather permitting.")
            charCount(200)
        }
    }
}