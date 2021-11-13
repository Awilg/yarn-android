package com.orienteer.profile

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel

data class CounterState(
    val count: Int = 0,
) : MavericksState

class CounterViewModel(initialState: CounterState) : MavericksViewModel<CounterState>(initialState) {
    fun incrementCount() = setState { copy(count = count + 1) }
}

class ProfileComposeActivity : Fragment() {

}