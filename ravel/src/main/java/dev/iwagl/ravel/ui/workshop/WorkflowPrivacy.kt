package dev.iwagl.ravel.ui.workshop

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.google.accompanist.insets.*

@Composable
fun WorkflowPrivacy(
    navToNextTask: () -> Unit,
    navToSummary: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_privacy_title), subtitle = stringResource(id = R.string.wf_privacy_subtitle))
    }
}

@Preview
@Composable
internal fun WorkflowPrivacy() {
    Text(
        text = "Privacy!",
        modifier = Modifier.statusBarsPadding()
    )
}