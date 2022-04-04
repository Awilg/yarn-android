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
fun WorkflowTipsTreasure(
    navToNextTask: () -> Unit,
    navToSummary: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_tips_treasure_title), subtitle = stringResource(id = R.string.wf_tips_treasure_subtitle))
    }
}

@Preview
@Composable
internal fun WorkflowTipsTreasure() {
    Text(
        text = "Tips & Treasure!",
        modifier = Modifier.statusBarsPadding()
    )
}