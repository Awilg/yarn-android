package dev.iwagl.ravel.ui.workshop

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.google.accompanist.insets.*
import dev.iwagl.ravel.R
import dev.iwagl.ravel.ui.common.*

@Composable
fun Workshop(
    openPreview: () -> Unit,
) {
    Workshop()
}

@Composable
internal fun Workshop() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        item { SectionTitle(stringResource(id = R.string.in_progress)) }
        item { WorkshopItem(onClick = {}) }
        item { SectionTitle(stringResource(id = R.string.completed)) }
        item { WorkshopItem(onClick = {}) }
        item { WorkshopItem(onClick = {}) }
        item { WorkshopItem(onClick = {}) }
        item { WorkshopItem(onClick = {}) }
        item { WorkshopItem(onClick = {}) }
        item { WorkshopItem(onClick = {}) }
        item { WorkshopItem(onClick = {}) }
        item { WorkshopItem(onClick = {}) }
    }
}

@Composable
internal fun WorkshopItem(onClick: (itemId: Long) -> Unit) {
    Card(modifier = Modifier.padding(start = 24.dp, end = 24.dp), elevation = 4.dp) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(1) }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .size(width = 240.dp, height = 100.dp)
                        .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                ) {
                    Text(
                        text = "Test Title",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Test subtitle",
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                }
                Column(modifier = Modifier
                    .padding(end = 24.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.seattle_kraken_secondary_logo),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(percent = 5))
                    )
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
            Divider()
            Spacer(modifier = Modifier.size(2.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "pending publication",
                    color = Color.Black,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }
        }
    }
}