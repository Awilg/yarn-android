package dev.iwagl.ravel

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.animation.*
import androidx.core.view.*
import com.google.accompanist.insets.*
import com.google.accompanist.permissions.*
import dagger.hilt.android.*
import dev.iwagl.ravel.ui.home.*
import dev.iwagl.ravel.ui.theme.*

@AndroidEntryPoint
class RavelActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            YarnandroidTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    Home()
                }
            }
        }
    }
}
