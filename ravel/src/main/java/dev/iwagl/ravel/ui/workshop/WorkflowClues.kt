package dev.iwagl.ravel.ui.workshop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
fun WorkflowClues(
    navToNextTask: () -> Unit,
    navToSummary: () -> Unit,
    navToClueSelection: () -> Unit
) {
    // This is by default scoped to the closest LifecycleOwner
    val viewModel: WorkflowViewModel = mavericksActivityViewModel()

    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_clues_title), subtitle = stringResource(id = R.string.wf_clues_subtitle))

        val cluesInState by viewModel.collectAsState { it.wfAdvClues }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(24.dp, 12.dp, 24.dp, 12.dp)
        ) {
            cluesInState?.let { clues ->
                items(clues) { item ->
                    ClueSummaryItem(item)
                }
            } ?: run {
                item { NoClueLabel() }
            }

            item {
                TextButton(onClick = navToClueSelection) {
                    Text(stringResource(id = R.string.button_action_add))
                }
            }
        }
    }
}

@Preview
@Composable
internal fun WorkflowClues() {
    Text(
        text = "Clues!",
        modifier = Modifier.statusBarsPadding()
    )
}

@Preview
@Composable
internal fun NoClueLabel() {
    Text(
        text = stringResource(id = R.string.wf_clues_no_clues_label)
    )
}

@Preview
@Composable
internal fun CluesExist() {
    Text(
        text = stringResource(id = R.string.wf_clues_no_clues_label)
    )
}


@Composable
internal fun ClueSummaryItem(clue: BaseClue) {
    Row {
        Text(clue.cluePrompt)
    }
}
