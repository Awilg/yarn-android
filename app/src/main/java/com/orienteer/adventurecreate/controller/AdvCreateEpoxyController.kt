package com.orienteer.adventurecreate.controller

import com.airbnb.epoxy.EpoxyController
import com.orienteer.advcreateCard
import com.orienteer.adventurecreate.models.AdvCreateSummary
import com.orienteer.adventurecreate.models.advCreateTextSection
import com.orienteer.buttonFull
import com.orienteer.sectionTitle

class AdvCreateEpoxyController(private val listener: AdvCreateEpoxyListener) : EpoxyController() {
    var inProgressAdvCreate: List<AdvCreateSummary> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    var completedAdvCreate: List<AdvCreateSummary> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        sectionTitle {
            id(hashCode())
            text("In progress")
        }

        inProgressAdvCreate.forEach {
            advcreateCard {
                id(hashCode())
                advCreateSummary(it)
            }
        }

        buttonFull {
            id(hashCode())
            buttonText("Create new adventure")
            onClickListener(listener)
        }

        sectionTitle {
            id(hashCode())
            text("Completed")
        }

        completedAdvCreate.forEach {
            advcreateCard {
                id(hashCode())
                advCreateSummary(it)
            }
        }

    }
}