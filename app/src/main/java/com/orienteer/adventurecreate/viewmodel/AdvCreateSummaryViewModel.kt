package com.orienteer.adventurecreate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.MavericksViewModel
import com.orienteer.adventurecreate.models.AdvCreateState
import com.orienteer.adventurecreate.models.AdvCreateSummaryViewState
import com.orienteer.adventurecreate.models.SectionItem

class AdvCreateSummaryViewModel(val initialState: AdvCreateState) :
    MavericksViewModel<AdvCreateState>(initialState) {

    private val _navToTitleAndInfo = MutableLiveData<Boolean>()
    val navToTitleAndInfo: LiveData<Boolean> = _navToTitleAndInfo

    private val _viewState = MutableLiveData<AdvCreateSummaryViewState>()
    val viewState: LiveData<AdvCreateSummaryViewState> = _viewState

    init {
        _viewState.value = AdvCreateSummaryViewState(listOf(
            (SectionItem("Title & Information",
                completed = true,
                continuable = false,
                onClickHandler = this::navToTitleAndInfo
            )),
            (SectionItem("Photos",
                completed = false,
                continuable = true,
                onClickHandler = this::navToTitleAndInfo
            ))))
    }

    private fun navToTitleAndInfo() {
        _navToTitleAndInfo.value = true
    }

    fun doneNavToTitleAndInfo() {
        _navToTitleAndInfo.value = false
    }
}