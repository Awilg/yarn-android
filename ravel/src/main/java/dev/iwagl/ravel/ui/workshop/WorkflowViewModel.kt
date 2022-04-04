package dev.iwagl.ravel.ui.workshop

import android.net.*
import com.airbnb.mvrx.*
import dev.iwagl.ravel.data.models.*


data class WorkflowState(
    val wfAdvTitle: String? = null,
    val wfAdvDescription: String? = null,
    val wfPhotos: List<Uri>? = null,
    val wfAdvClues: List<BaseClue>? = null,
    val wfCurrentClue: BaseClue? = null
) : MavericksState

class WorkflowViewModel(wfState: WorkflowState
) : MavericksViewModel<WorkflowState>(wfState) {

    fun updateTitle(title: String) {
        setState { copy(wfAdvTitle = title) }
    }

    fun updateDescription(description: String) {
        setState { copy(wfAdvDescription = description) }
    }

    fun saveTitleInfo() {
        // persist the title and description to backend
    }

    fun saveTextClue(prompt: String, answer: String) {
        setState {
            copy(wfAdvClues = wfAdvClues?.plus(ClueText(cluePrompt = prompt, answer = answer)) ?: run {
                listOf(ClueText(cluePrompt = prompt, answer = answer))
            })
        }
    }

    fun saveCurrentPhotoClue(prompt: String = "", imageUris: List<Uri> = emptyList(), doneEditing : Boolean = false) {
        setState {
            var updatedClue : BaseClue? = (this.wfCurrentClue as CluePhoto?)?.let { clue ->
                CluePhoto(cluePrompt = clue.cluePrompt, imgUris = clue.imgUris)
            } ?: run {
                CluePhoto(cluePrompt = prompt, imgUris = imageUris)
            }

            var updatedClues = wfAdvClues
            if (doneEditing) {
                updatedClues = wfAdvClues?.plus(updatedClue!!) ?: run {
                    listOf(updatedClue!!)
                }
                updatedClue = null
            }
            copy(wfCurrentClue = updatedClue, wfAdvClues = updatedClues)
        }
    }

    fun savePhoto(it: Uri) {
        setState {
            copy(wfPhotos = wfPhotos?.plus(it) ?: run { listOf(it) })
        }
    }
}