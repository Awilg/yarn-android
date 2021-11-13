package dev.iwagl.ravel

import android.app.*
import dagger.hilt.android.*
import dev.iwagl.ravel.appinits.*
import javax.inject.*

@HiltAndroidApp
class RavelApplication : Application() {
    @Inject lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }
}
