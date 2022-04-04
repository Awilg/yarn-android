package dev.iwagl.ravel.ui.workshop

import android.media.*
import android.util.*
import android.widget.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.airbnb.mvrx.compose.*
import com.google.accompanist.insets.*
import com.google.accompanist.permissions.*


@ExperimentalPermissionsApi
@Composable
fun WorkflowPhotos(
    navToNextTask: () -> Unit,
    navToSummary: () -> Unit
) {
    // This is by default scoped to the closest LifecycleOwner
    val viewModel: WorkflowViewModel = mavericksActivityViewModel()
    val context = LocalContext.current
    var showGallery by remember { mutableStateOf(false) }

    val photoUris = viewModel.collectAsState { it.wfPhotos }.value

    Column(modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowHeader(title = stringResource(id = R.string.wf_photos_title), subtitle = stringResource(id = R.string.wf_photos_subtitle))

        Column(modifier = Modifier.padding(24.dp, 12.dp, 24.dp, 12.dp)) {

            TextButton(onClick = {
                showGallery = true
            }) {
                Text(stringResource(id = R.string.button_action_upload))
            }

            photoUris?.forEach {
                // Load thumbnail of a specific media item.
                val thumbnail: Bitmap? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        context.contentResolver.loadThumbnail(
                            it, Size(640, 480), null)
                    } else {
                        MediaStore.Images.Media.getBitmap(context.contentResolver,it)
                    }
                thumbnail?.let {  btm ->
                    Image(bitmap = btm.asImageBitmap(),
                        contentDescription =null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(75.dp))
                }
            }

            TextButton(onClick = {
                navToNextTask()
            }) {
                Text(stringResource(id = R.string.button_action_next))
            }

            if (showGallery) {
                GallerySelect(onImageUri = {
                    viewModel.savePhoto(it)
                    showGallery = false
                })
            }
        }
    }
}

@Preview
@Composable
internal fun WorkflowPhotos() {
    Text(
        text = "Photos!",
        modifier = Modifier.statusBarsPadding()
    )
}