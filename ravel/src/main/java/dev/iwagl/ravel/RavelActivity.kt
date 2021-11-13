package dev.iwagl.ravel

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.core.view.*
import com.google.accompanist.insets.*
import dagger.hilt.android.*
import dev.iwagl.ravel.ui.home.*
import dev.iwagl.ravel.ui.theme.*

@AndroidEntryPoint
class RavelActivity : ComponentActivity() {
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
