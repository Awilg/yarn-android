package dev.iwagl.ravel.ui.workshop

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.airbnb.mvrx.compose.*
import com.google.accompanist.insets.*


@Composable
fun WorkflowTitleInfo(
    navToNextTask: () -> Unit,
    navToSummary: () -> Unit
) {
    // This is by default scoped to the closest LifecycleOwner
    val viewModel: WorkflowViewModel = mavericksActivityViewModel()

    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_title_info_header_title), subtitle = stringResource(id = R.string.wf_title_info_header_subtitle))

        WorkflowTextField(text = viewModel.collectAsState { it.wfAdvTitle }.value ?: "", onValueChange = { x -> viewModel.updateTitle(x) }, hint = stringResource(id = R.string.wf_title_info_title_hint))
        WorkflowTextField(text = viewModel.collectAsState { it.wfAdvDescription }.value ?: "", onValueChange = { x -> viewModel.updateDescription(x) }, hint = stringResource(id = R.string.wf_title_info_description_hint))
    }
}

@Preview
@Composable
internal fun WorkflowTitleInfo() {
    Text(
        text = "Title & Info!",
        modifier = Modifier.statusBarsPadding()
    )
}