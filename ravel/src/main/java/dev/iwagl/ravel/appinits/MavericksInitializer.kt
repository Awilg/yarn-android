package dev.iwagl.ravel.appinits

import android.app.*
import com.airbnb.mvrx.*
import javax.inject.*

class MavericksInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) = Mavericks.initialize(application.baseContext)
}