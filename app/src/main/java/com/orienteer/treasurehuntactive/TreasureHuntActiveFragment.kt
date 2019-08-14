package com.orienteer.treasurehuntactive

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.orienteer.R
import com.orienteer.camera.PERMISSIONS_RC_CAMERA
import com.orienteer.core.ClueAdapter
import com.orienteer.core.ClueAdapterListener
import com.orienteer.databinding.FragmentTreasureHuntActiveBinding
import com.orienteer.models.Clue
import com.orienteer.models.ClueType
import com.orienteer.models.RequestCodes
import com.orienteer.treasurehunts.TreasureHuntsViewModel
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import timber.log.Timber

class TreasureHuntActiveFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    /**
     * Lazily initialize our [TreasureHuntActiveViewModel].
     */
    private lateinit var viewModel: TreasureHuntActiveViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTreasureHuntActiveBinding.inflate(inflater)
        val application = requireNotNull(activity).application

        // Initialize the viewmodel
        val treasureHunt = TreasureHuntActiveFragmentArgs.fromBundle(arguments!!).selectedTreasureHunt
        val viewModelFactory = TreasureHuntActiveViewModelFactory(treasureHunt, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TreasureHuntActiveViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.cluesRecyclerview.adapter = ClueAdapter(object : ClueAdapterListener {
            override fun clueTypeOnClick(type: ClueType) {
                Toast.makeText(context, "Clicked Type: ${type.name}", Toast.LENGTH_SHORT).show()
            }

            override fun clueSolveOnClick(clue: Clue) {
                when (clue.type) {
                    ClueType.Photo -> {
                        viewModel.setCurrentClue(clue)
                        // save clue to viewmodel and retrieve it in permission granted listener
                        navigateToCameraForClue(clue)
                    }
                    ClueType.Location -> TODO()
                    ClueType.Text -> TODO()
                }
            }

            override fun clueHintOnClick(hint: String) {
                Toast.makeText(context, "Clicked Hint: $hint", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    private fun navigateToCameraForClue(clue: Clue) {
        Timber.i("Attempting to navigate to camera clue solver.")
        if (EasyPermissions.hasPermissions(context!!, Manifest.permission.CAMERA)) {
            findNavController()
                .navigate(
                    TreasureHuntActiveFragmentDirections.actionTreasureHuntActiveFragmentToCameraFragment(clue)
                )
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, RequestCodes.PERMISSIONS_RC_CAMERA.code,
                    Manifest.permission.CAMERA).build()
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            RequestCodes.PERMISSIONS_RC_CAMERA.code -> {
                findNavController().navigate(
                    TreasureHuntActiveFragmentDirections
                        .actionTreasureHuntActiveFragmentToCameraFragment(viewModel.currentActiveClue.value!!))
                Timber.i("GRANTED CAMERA!")
            }
        }
    }
}
