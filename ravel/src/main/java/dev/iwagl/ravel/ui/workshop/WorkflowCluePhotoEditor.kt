package dev.iwagl.ravel.ui.workshop

import android.*
import android.content.*
import android.net.*
import android.os.*
import android.provider.*
import android.widget.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.airbnb.mvrx.compose.*
import com.google.accompanist.insets.*
import com.google.accompanist.permissions.*
import dev.iwagl.ravel.ui.common.*

@ExperimentalPermissionsApi
@Composable
fun WorkflowCluePhotoEditor(
    navToClueSummary: () -> Unit
) {
    // This is by default scoped to the closest LifecycleOwner
    val viewModel: WorkflowViewModel = mavericksActivityViewModel()
    val context = LocalContext.current
    var showGallery by remember { mutableStateOf(false) }
    var prompt by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_clue_photos_title), subtitle = stringResource(id = R.string.wf_clue_photos_subtitle))

        Column(modifier = Modifier.padding(24.dp, 12.dp, 24.dp, 12.dp)) {
            Text(stringResource(id = R.string.wf_clues_clue_prompt))
            WorkflowTextField(
                text = prompt,
                onValueChange = { prompt = it },
                hint = stringResource(id = R.string.wf_clues_text_prompt_hint)
            )
            Text(stringResource(id = R.string.wf_clues_clue_solution))

            TextButton(onClick = {
                viewModel.saveCurrentPhotoClue(prompt = prompt)
                showGallery = true
            }) {
                Text(stringResource(id = R.string.button_action_upload))
            }

            TextButton(onClick = {
                viewModel.saveCurrentPhotoClue(prompt = prompt, doneEditing = true)
                navToClueSummary()
            }) {
                Text(stringResource(id = R.string.button_action_add))
            }

            if (showGallery) {
                GallerySelect(onImageUri = {
                    Toast.makeText(context, "onImageUri! - $it", Toast.LENGTH_SHORT).show()
                    showGallery = false
                })
            }
        }
    }
}