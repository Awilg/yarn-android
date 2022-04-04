package dev.iwagl.ravel.ui.workshop

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import com.google.accompanist.insets.*

@Composable
fun WorkflowPreview(
    navToNextTask: () -> Unit,
    navToSummary: () -> Unit
) {
    WorkflowPreview()
}

@Preview
@Composable
internal fun WorkflowPreview() {
    Text(
        text = "Preview!",
        modifier = Modifier.statusBarsPadding()
    )
}