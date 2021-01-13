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
import com.orienteer.adventurecreate.models.SectionType
import com.orienteer.map.DEFAULT_LOCATION
import com.orienteer.map.DEFAULT_ZOOM
import com.orienteer.models.ClueLocation
import com.orienteer.models.CluePhoto
import com.orienteer.models.ClueText
import com.orienteer.models.ClueType

class AdvCreateSummaryViewModel(initialState: AdvCreateState) :
    MavericksViewModel<AdvCreateState>(initialState) {

    private val _currentClueTypeSelection = MutableLiveData<ClueType>()
    val currentClueTypeSelection: LiveData<ClueType> = _currentClueTypeSelection

    private var _currentLocationClueMap: GoogleMap? = null

    private val _lastKnownLocation = MutableLiveData<Location>()
    val lastKnownLocation: LiveData<Location>
        get() = _lastKnownLocation

    init {
    }

    fun updateTitle(t: CharSequence) {
        setState { copy(title = t.toString()) }
    }

    fun updateDescription(desc: CharSequence) {
        setState { copy(description = desc.toString()) }
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
                currentLocCluePrompt = null,
                currentLocClueLatLng = null
            )
        }
    }

    fun savePhotoClue() {
        setState {
            copy(
                clues = clues.plus(
                    CluePhoto(
                        cluePrompt = currentPhotoCluePrompt ?: ""
                    )
                ),
                currentPhotoCluePrompt = null
            )
        }
    }

    fun updateCurrentPhotoCluePhotos(bitmaps: List<Bitmap>) {
        setState { copy(currentPhotoCluePhotos = bitmaps) }
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

    fun saveTitleInfo() {
        // TODO:save to DB
        setState { copy(currentFocusedSectionItem = SectionType.Photos) }
    }

    fun savePhotos() {
        setState { copy(currentFocusedSectionItem = SectionType.Clues) }
    }

    fun saveClues() {
        setState { copy(currentFocusedSectionItem = SectionType.Publishing) }
    }

    fun savePublishing() {
        setState { copy(currentFocusedSectionItem = SectionType.TipsAndTreasure) }
    }

    fun saveTips() {
        setState { copy(currentFocusedSectionItem = SectionType.Review) }
    }

    fun updateAdventurePrivacy(isPrivate: Boolean?) {
        setState { copy(isPrivateAdventure = isPrivate) }
    }

    fun updateTipsEnabled(tipsEnabled: Boolean?) {
        setState { copy(tipsEnabled = tipsEnabled) }
    }

    fun updateTreasureEnabled(treasureEnabled: Boolean?) {
        setState { copy(treasureEnabled = treasureEnabled) }
    }

//fun saveAdventure() {
//    _currentAdventure.value?.let { adventureCreate ->
//        coroutineScope.launch {
//
//            _clues.value?.let { clues ->
//                adventureCreate.clues = clues
//            }
//            // Get the Deferred object for our Retrofit request
//            val saveAdventure = TreasureHuntApi.service.saveAdventure(adventureCreate)
//            try {
//                // this will run on a thread managed by Retrofit
//                val result = saveAdventure.await()
//                //_currentUser.value = result
//            } catch (e: Exception) {
//                //_status.value = ApiStatus.ERROR
//                Timber.e("Network request failed with exception $e")
//                //_currentUser.value = TEST_USER
//            }
//        }
//    }
//}
}