package com.orienteer.util

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.withState

/**
 * For use with [MavericksBaseFragment.epoxyController].
 *
 * This builds Epoxy models in a background thread.
 */
internal open class MavericksEpoxyController(
    val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {

    override fun buildModels() {
        buildModelsCallback()
    }
}

/**
 * Create a [MavericksEpoxyController] that builds models with the given callback.
 */
fun MavericksBaseFragment.simpleController(
    buildModels: EpoxyController.() -> Unit
): EpoxyController = MavericksEpoxyController {
    // Models are built asynchronously, so it is possible that this is called after the fragment
    // is detached under certain race conditions.
    if (view == null || isRemoving) return@MavericksEpoxyController
    buildModels()
}


/**
 * Create a [MavericksEpoxyController] that builds models with the given callback.
 * When models are built the current state of the viewmodel will be provided.
 */
fun <S : MavericksState, A : MavericksViewModel<S>> MavericksBaseFragment.simpleController(
    viewModel: A,
    buildModels: EpoxyController.(state: S) -> Unit
): EpoxyController = MavericksEpoxyController {
    if (view == null || isRemoving) return@MavericksEpoxyController
    withState(viewModel) { state ->
        buildModels(state)
    }
}