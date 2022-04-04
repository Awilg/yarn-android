package dev.iwagl.ravel.ui.workshop

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.airbnb.mvrx.compose.*
import com.google.accompanist.insets.*

@Composable
fun WorkflowClueLocationEditor(
    navToClueSummary: () -> Unit,
    navToClueLocationSelection: () -> Unit
) {
    // This is by default scoped to the closest LifecycleOwner
    val viewModel: WorkflowViewModel = mavericksActivityViewModel()

    var prompt by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_clue_location_title), subtitle = stringResource(id = R.string.wf_clue_location_subtitle))

        Column(modifier = Modifier.padding(24.dp, 12.dp, 24.dp, 12.dp)) {
            Text(stringResource(id = dev.iwagl.ravel.R.string.wf_clues_clue_prompt))
            WorkflowTextField(
                text = prompt,
                onValueChange = { prompt = it },
                hint = stringResource(id = R.string.wf_clues_text_prompt_hint)
            )
            Text(stringResource(id = R.string.wf_clues_clue_solution))
            TextButton(onClick = {
                // viewModel.saveTextClue(prompt, answer)
                navToClueLocationSelection()
            }) {
                Text(stringResource(id = R.string.wf_clue_location_choose_on_map))
            }
        }
    }
}