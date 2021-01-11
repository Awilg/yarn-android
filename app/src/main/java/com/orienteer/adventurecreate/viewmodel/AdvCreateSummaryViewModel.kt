package com.orienteer.adventurecreate.viewmodel

import android.graphics.Bitmap
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.MavericksViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.orienteer.adventurecreate.models.AdvCreateState
import com.orienteer.adventurecreate.models.AdvCreateSummaryViewState
import com.orienteer.adventurecreate.models.SectionItem
import com.orienteer.map.DEFAULT_LOCATION
import com.orienteer.map.DEFAULT_ZOOM
import com.orienteer.models.ClueLocation
import com.orienteer.models.ClueText
import com.orienteer.models.ClueType

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

    private val _currentClueTypeSelection = MutableLiveData<ClueType>()
    val currentClueTypeSelection: LiveData<ClueType> = _currentClueTypeSelection

    private var _currentLocationClueMap: GoogleMap? = null

    private val _lastKnownLocation = MutableLiveData<Location>()
    val lastKnownLocation: LiveData<Location>
        get() = _lastKnownLocation

    init {

        _viewState.value = AdvCreateSummaryViewState(
            listOf(
                (SectionItem(
                    "Title & Information",
                    completed = true,
                    continuable = false,
                    onClickHandler = this::navToTitleAndInfo
                )),
                (SectionItem(
                    "Photos",
                    completed = false,
                    continuable = true,
                    onClickHandler = this::navToPhotos
                )),
                (SectionItem(
                    "Publishing",
                    completed = false,
                    continuable = false,
                    onClickHandler = this::navToPublishing
                )),
                (SectionItem(
                    "Clues",
                    completed = false,
                    continuable = false,
                    onClickHandler = this::navToClues
                )),

                (SectionItem(
                    "Tips & Treasure",
                    completed = false,
                    continuable = false,
                    onClickHandler = this::navToTips
                )),

                (SectionItem(
                    "Review",
                    completed = false,
                    continuable = false,
                    onClickHandler = this::navToReview
                )),
            )
        )

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

    fun updatePhotos(bitmaps: List<Bitmap>) {
        setState { copy(photos = bitmaps) }
    }

    fun updateCurrentClueTypeSelected(clueType: ClueType?) {
        _currentClueTypeSelection.value = clueType
    }

    fun updateTextClueAnswer(answer: String?) {
        setState { copy(currentTextClueAnswer = answer) }
    }

    fun saveTextClue() {
        setState {
            copy(
                clues = clues.plus(
                    ClueText(
                        cluePrompt = currentTextCluePrompt ?: "",
                        answer = currentTextClueAnswer ?: ""
                    )
                ),
                currentTextClueAnswer = null,
                currentTextCluePrompt = null
            )
        }
    }

    fun setCurrentClueLocationMap(map: GoogleMap?) {
        _currentLocationClueMap = map
    }

    fun updateClueLocationMap() {
        if (_lastKnownLocation.value == null) {
            resetClueLocationMap()
        } else {
            _currentLocationClueMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        _lastKnownLocation.value!!.latitude,
                        _lastKnownLocation.value!!.longitude
                    ), DEFAULT_ZOOM
                )
            )
        }
    }

    fun setLastKnownLocation(loc: Location?) {
        _lastKnownLocation.value = loc
        updateClueLocationMap()
    }

    fun resetClueLocationMap() {
        _currentLocationClueMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                DEFAULT_LOCATION,
                DEFAULT_ZOOM
            )
        )
    }

    fun saveClueLocationCenterMap() {
        val latlng = _currentLocationClueMap?.cameraPosition?.target
        setState { copy(currentLocClueLatLng = latlng) }
    }

    fun saveLocationClue() {
        setState {
            copy(
                clues = clues.plus(
                    ClueLocation(
                        cluePrompt = currentLocCluePrompt ?: "",
                        location = LatLng(
                            currentLocClueLatLng!!.latitude,
                            currentLocClueLatLng.longitude
                        )
                    )
                ),
                currentLocCluePrompt = null
            )
        }
    }

    fun updateCluePrompt(prompt: String?, type: ClueType) {
        when (type) {
            ClueType.Text -> setState { copy(currentTextCluePrompt = prompt) }
            ClueType.Location -> setState { copy(currentLocCluePrompt = prompt) }
            ClueType.Photo -> setState { copy(currentPhotoCluePrompt = prompt) }
            else -> {
            }
        }
    }
}