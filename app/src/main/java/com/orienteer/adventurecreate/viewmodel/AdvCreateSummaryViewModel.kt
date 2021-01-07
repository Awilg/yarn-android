package com.orienteer.adventurecreate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.MavericksViewModel
import com.orienteer.adventurecreate.models.AdvCreateState
import com.orienteer.adventurecreate.models.AdvCreateSummaryViewState
import com.orienteer.adventurecreate.models.SectionItem

class AdvCreateSummaryViewModel(initialState: AdvCreateState) :
    MavericksViewModel<AdvCreateState>(initialState) {

    private val _navToTitleAndInfo = MutableLiveData<Boolean>()
    val navToTitleAndInfo: LiveData<Boolean> = _navToTitleAndInfo

    private val _navToPhotos = MutableLiveData<Boolean>()
    val navToPhotos: LiveData<Boolean> = _navToPhotos

    private val _navToPublishing = MutableLiveData<Boolean>()
    val navToPublishing: LiveData<Boolean> = _navToPublishing

    private val _navToClues = MutableLiveData<Boolean>()
    val navToClues: LiveData<Boolean> = _navToClues

    private val _navToTips = MutableLiveData<Boolean>()
    val navToTips: LiveData<Boolean> = _navToTips

    private val _navToReview = MutableLiveData<Boolean>()
    val navToReview: LiveData<Boolean> = _navToReview

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
                onClickHandler = this::navToPhotos
            )),
            (SectionItem("Publishing",
                completed = false,
                continuable = false,
                onClickHandler = this::navToPublishing
            )),
            (SectionItem("Clues",
                completed = false,
                continuable = false,
                onClickHandler = this::navToClues
            )),

            (SectionItem("Tips & Treasure",
                completed = false,
                continuable = false,
                onClickHandler = this::navToTips
            )),

            (SectionItem("Review",
                completed = false,
                continuable = false,
                onClickHandler = this::navToReview
            )),
            ))

    }

    private fun navToTitleAndInfo() {
        _navToTitleAndInfo.value = true
    }

    fun doneNavToTitleAndInfo() {
        _navToTitleAndInfo.value = false
    }

    private fun navToPhotos() {
        _navToPhotos.value = true
    }

    fun doneNavToPhotos() {
        _navToPhotos.value = false
    }

    private fun navToPublishing() {
        _navToPublishing.value = true
    }

    fun doneNavToPublishing() {
        _navToPublishing.value = false
    }

    private fun navToClues() {
        _navToClues.value = true
    }

    fun doneNavToClues() {
        _navToClues.value = false
    }

    private fun navToTips() {
        _navToTips.value = true
    }

    fun doneNavToTips() {
        _navToTips.value = false
    }

    private fun navToReview() {
        _navToReview.value = true
    }

    fun doneNavToReview() {
        _navToReview.value = false
    }

    fun updateTitle(t: CharSequence) {
        setState { copy(title = t.toString()) }
    }

    fun updateDescription(desc: CharSequence) {
        setState { copy(title = desc.toString()) }
    }

}