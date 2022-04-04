package dev.iwagl.ravel.ui.workshop

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import com.google.accompanist.insets.*


@Composable
fun WorkflowReview(
    navToNextTask: () -> Unit,
    navToSummary: () -> Unit
) {
    WorkflowReview()
}

@Preview
@Composable
internal fun WorkflowReview() {
    Text(
        text = "Review!",
        modifier = Modifier.statusBarsPadding()
    )
}