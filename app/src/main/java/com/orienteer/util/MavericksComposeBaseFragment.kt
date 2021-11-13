package com.orienteer.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView

/**
 * Provides base UI (epoxy, toolbar, back handling) for fragments to extend.
 */
abstract class MavericksComposeBaseFragment : Fragment(), MavericksView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent()
    }

    open fun setContent() {

    }
}

