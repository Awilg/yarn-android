package dev.iwagl.ravel.ui.workshop

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.google.accompanist.insets.*


@Composable
fun WorkflowSummary(
    navToTitleInfo: () -> Unit,
    navToPhotos: () -> Unit,
    navToPrivacy: () -> Unit,
    navToClues: () -> Unit,
    navToTipsTreasure: () -> Unit,
    navToReview: () -> Unit,
    navToPreview: () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {

        WorkflowSummaryHeader()
        WorkflowSummaryItemList(navToTitleInfo, navToPhotos, navToPrivacy, navToClues, navToTipsTreasure, navToReview, navToPreview)
    }
}

@Preview
@Composable
internal fun WorkflowSummaryHeader() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFFFF8E1))) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.marginalia), contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
            )
            Text(
                text = "Let's make something great!"
            )
        }
    }
}

@Composable
internal fun WorkflowSummaryItemList(navToTitleInfo: () -> Unit, navToPhotos: () -> Unit, navToPrivacy: () -> Unit, navToClues: () -> Unit, navToTipsTreasure: () -> Unit, navToReview: () -> Unit, navToPreview: () -> Unit) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(start = 24.dp, end = 24.dp)
    ) {
        item {
            WorkflowSummaryItem(
                stringResource(id = R.string.wf_summary_title_info),
                navToTitleInfo
            )
        }
        item {
            WorkflowSummaryItem(
                stringResource(id = R.string.wf_summary_photos),
                navToPhotos
            )
        }
        item {
            WorkflowSummaryItem(
                stringResource(id = R.string.wf_summary_privacy),
                navToPrivacy
            )
        }
        item { WorkflowSummaryItem(stringResource(id = R.string.wf_summary_clues), navToClues) }
        item {
            WorkflowSummaryItem(
                stringResource(id = R.string.wf_summary_tips_treasure),
                navToTipsTreasure
            )
        }
        item {
            WorkflowSummaryItem(
                stringResource(id = R.string.wf_summary_review),
                navToReview
            )
        }
        item {
            WorkflowSummaryItem(
                stringResource(id = R.string.wf_summary_preview),
                navToPreview
            )
        }
    }
}

@Composable
internal fun WorkflowSummaryItem(title: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = title)

        Button(onClick = onClick,
            contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp)) {
            Text("Continue")
        }
    }
}