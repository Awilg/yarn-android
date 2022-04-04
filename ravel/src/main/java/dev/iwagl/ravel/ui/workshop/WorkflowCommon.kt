package dev.iwagl.ravel.ui.workshop

import android.*
import android.content.*
import android.net.*
import android.os.*
import android.provider.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.google.accompanist.permissions.*
import dev.iwagl.ravel.ui.common.*


@Preview
@Composable
internal fun WorkflowHeader() {
    WorkflowHeader("This is a title!", "This is a subtitle!")
}

@Composable
internal fun WorkflowHeader(title: String, subtitle: String = "") {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFFFF8E1))
        .padding(top = 32.dp, bottom = 32.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = title,
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
            Text(
                text = subtitle,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp)
            )
        }
    }
}


@Preview
@Composable
internal fun WorkflowTextField() {
    WorkflowTextField("This is a hint!", { })
}

@Composable
internal fun WorkflowTextField(text: String,  onValueChange: (String) -> Unit, hint: String? = null, chars: Int? = null) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = { hint?.let { Text(it) } },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
    ))
}


val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

@ExperimentalPermissionsApi
@Composable
fun GallerySelect(
    modifier: Modifier = Modifier,
    onImageUri: (Uri) -> Unit = { }
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageUri(uri ?: EMPTY_IMAGE_URI)
        }
    )

    @Composable
    fun LaunchGallery() {
        SideEffect {
            launcher.launch("image/*")
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Permission(
            permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
            rationale = "You want to read from photo gallery, so I'm going to have to ask for permission.",
            permissionNotAvailableContent = {
                Column(modifier) {
                    Text("O noes! No Photo Gallery!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                })
                            }
                        ) {
                            Text("Open Settings")
                        }
                        // If they don't want to grant permissions, this button will result in going back
                        Button(
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                onImageUri(EMPTY_IMAGE_URI)
                            }
                        ) {
                            Text("Use Camera")
                        }
                    }
                }
            },
        ) {
            LaunchGallery()
        }
    } else {
        LaunchGallery()
    }
}
