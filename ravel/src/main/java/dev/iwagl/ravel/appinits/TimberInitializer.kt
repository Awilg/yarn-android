package dev.iwagl.ravel.appinits

import android.app.*
import dev.iwagl.ravel.*
import dev.iwagl.ravel.util.*
import javax.inject.*

class TimberInitializer @Inject constructor(
    private val ravelLogger: RavelLogger
) : AppInitializer {
    override fun init(application: Application) = ravelLogger.setup(BuildConfig.DEBUG)
}
