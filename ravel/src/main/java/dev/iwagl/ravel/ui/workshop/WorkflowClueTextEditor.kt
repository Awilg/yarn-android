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
fun WorkflowClueTextEditor(
    navToClueSummary: () -> Unit
) {
    // This is by default scoped to the closest LifecycleOwner
    val viewModel: WorkflowViewModel = mavericksActivityViewModel()

    var prompt by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_clues_text_title), subtitle = stringResource(id = dev.iwagl.ravel.R.string.wf_clues_text_subtitle))

        Column(modifier = Modifier.padding(24.dp, 12.dp, 24.dp, 12.dp)) {
            Text(stringResource(id = R.string.wf_clues_clue_prompt))
            WorkflowTextField(
                text = prompt,
                onValueChange = { prompt = it },
                hint = stringResource(id = R.string.wf_clues_text_prompt_hint)
            )
            Text(stringResource(id = R.string.wf_clues_clue_solution))
            WorkflowTextField(
                text = answer,
                onValueChange = { answer = it },
                hint = stringResource(id = R.string.wf_clues_text_solution_hint)
            )


            TextButton(onClick = {
                viewModel.saveTextClue(prompt, answer)
                navToClueSummary()
            }) {
                Text(stringResource(id = R.string.button_action_add))
            }
        }
    }
}