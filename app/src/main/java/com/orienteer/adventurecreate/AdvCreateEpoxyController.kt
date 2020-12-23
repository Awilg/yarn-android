package com.orienteer.adventurecreate

import com.airbnb.epoxy.EpoxyController
import com.orienteer.advcreateCard
import com.orienteer.adventurecreate.models.AdvCreateSummary
import com.orienteer.buttonFull
import com.orienteer.sectionTitle

class AdvCreateEpoxyController : EpoxyController() {
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