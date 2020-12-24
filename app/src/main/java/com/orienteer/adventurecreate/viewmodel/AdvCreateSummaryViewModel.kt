package com.orienteer.adventurecreate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orienteer.adventurecreate.controller.AdvCreateSummaryEpoxyListener
import com.orienteer.adventurecreate.models.AdvCreateSummaryViewState
import com.orienteer.adventurecreate.models.SectionItem

class AdvCreateSummaryViewModel : ViewModel(), AdvCreateSummaryEpoxyListener {

    private val _navBack = MutableLiveData<Boolean>()
    val navBack: LiveData<Boolean> =_navBack

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

    override fun navBack() {
        TODO("Not yet implemented")
    }

    override fun navToTitleAndInfo() {
        _navToTitleAndInfo.value = true
    }

    fun doneNavToTitleAndInfo() {
        _navToTitleAndInfo.value = false
    }

    override fun navToPhotos() {
        TODO("Not yet implemented")
    }

    override fun navToPrivacy() {
        TODO("Not yet implemented")
    }

    override fun navToClues() {
        TODO("Not yet implemented")
    }

    override fun navToTipsAndTreasure() {
        TODO("Not yet implemented")
    }

    override fun navToReview() {
        TODO("Not yet implemented")
    }

    override fun navToPreview() {
        TODO("Not yet implemented")
    }
}