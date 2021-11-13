package dev.iwagl.ravel.ui.profile

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.google.accompanist.insets.*

@Composable
fun Profile(
    openPreview: () -> Unit,
) {
    Profile()
}

@Composable
internal fun Profile() {
    Text(
        text = "Profile!",
        modifier = Modifier.statusBarsPadding()
    )
}
