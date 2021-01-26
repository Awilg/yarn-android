package com.orienteer.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.MavericksView
import com.orienteer.databinding.MavericksFragmentBaseBinding
import pub.devrel.easypermissions.EasyPermissions

/**
 * Provides base UI (epoxy, toolbar, back handling) for fragments to extend.
 */
abstract class MavericksBaseFragment : Fragment(), MavericksView {

    // TODO: figure out how to specify a base layout via annotation
    protected lateinit var recyclerView: EpoxyRecyclerView
    protected lateinit var toolbar: Toolbar
    protected val epoxyController by lazy { epoxyController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!onBackPressed()) {
                        // Pass on back press to other listeners, or the activity's default handling
                        isEnabled = false
                        requireActivity().onBackPressed()
                        isEnabled = true
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MavericksFragmentBaseBinding.inflate(inflater)
        recyclerView = binding.recyclerView
        toolbar = binding.toolbar

        recyclerView.setController(epoxyController)

        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        // We don't want a title shown. By default it adds "Mavericks"
        activity?.title = ""

        return binding.root
    }

    protected open fun onBackPressed(): Boolean = false

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    /**
     * Provide the EpoxyController to use when building models for this Fragment.
     * Basic usages can simply use [simpleController]
     */
    abstract fun epoxyController(): EpoxyController

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }
}

abstract class MavericksWithPermissionsFragment : MavericksBaseFragment() {
    // Override the onresult callback to decorate the easy permissions one
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}

abstract class EasyPermissionsFragment : Fragment() {
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
