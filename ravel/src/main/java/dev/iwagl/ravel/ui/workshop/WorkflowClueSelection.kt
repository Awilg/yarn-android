package dev.iwagl.ravel.ui.workshop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.airbnb.mvrx.compose.*
import com.google.accompanist.insets.*
import dev.iwagl.ravel.data.models.*


@Composable
fun WorkflowClueSelection(
    navToClueEditor: (ClueType) -> Unit
) {
    // This is by default scoped to the closest LifecycleOwner
    val viewModel: WorkflowViewModel = mavericksActivityViewModel()

    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_clues_selection_title), subtitle = stringResource(id = dev.iwagl.ravel.R.string.wf_clues_selection_subtitle))

        // Probably should move this to the viewmodel but seems fine for this1
        var currSelected by remember { mutableStateOf(ClueType.TEXT) }

        Column(modifier = Modifier.padding(24.dp, 12.dp, 24.dp, 12.dp)) {
            MultiToggleButton(currentSelection = currSelected, toggleStates = listOf(ClueType.TEXT, ClueType.PHOTO, ClueType.LOCATION), onToggleChange = {currSelected = it})
            TextButton(onClick = { navToClueEditor(currSelected) }) {
                Text(stringResource(id = R.string.button_action_add))
            }
        }
    }
}

@Preview
@Composable
fun MultiToggleButton() {
    var currSelected by remember { mutableStateOf(ClueType.TEXT) }

    MultiToggleButton(currentSelection = currSelected, toggleStates = listOf(ClueType.TEXT, ClueType.PHOTO, ClueType.LOCATION), onToggleChange = {currSelected = it})
}

@Composable
fun MultiToggleButton(
    currentSelection: ClueType,
    toggleStates: List<ClueType>,
    onToggleChange: (ClueType) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)){
        toggleStates.forEach { toggleState ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = currentSelection == toggleState,
                        enabled = true,
                        onValueChange = { onToggleChange(toggleState) })
            ) {
                Text(toggleState.name)

                Checkbox(checked = currentSelection == toggleState, onCheckedChange = { onToggleChange(toggleState) })
            }

        }
    }
}