package com.orienteer.adventurecreate.controller

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.TypedEpoxyController
import com.orienteer.adventurecreate.models.AdvCreateSummaryViewState
import com.orienteer.createSummaryHeader
import com.orienteer.createSummaryItem
import com.orienteer.createSummaryItemCurrent

class AdvCreateSummaryController() :
    TypedEpoxyController<AdvCreateSummaryViewState>(
        EpoxyAsyncUtil.getAsyncBackgroundHandler(),
        EpoxyAsyncUtil.getAsyncBackgroundHandler()) {

    override fun buildModels(data: AdvCreateSummaryViewState?) {
        createSummaryHeader {
            id(hashCode())
            header("Let's make something together")
        }

        data?.sectionItems?.forEach {
            if (it.continuable) {
                createSummaryItemCurrent {
                    id(hashCode())
                    item(it)
                    onClick {_ -> it.onClickHandler()}
                }
            } else {
                createSummaryItem {
                    id(hashCode())
                    item(it)
                    onClick {_ -> it.onClickHandler()}
                }
            }
        }
    }
}