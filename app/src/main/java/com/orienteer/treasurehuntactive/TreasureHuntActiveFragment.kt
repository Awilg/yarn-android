package com.orienteer.treasurehuntactive

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.orienteer.core.ClueAdapter
import com.orienteer.core.ClueAdapterListener
import com.orienteer.databinding.FragmentTreasureHuntActiveBinding
import com.orienteer.models.Clue
import com.orienteer.models.ClueState
import com.orienteer.models.ClueType
import com.orienteer.models.RequestCodes
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import timber.log.Timber

class TreasureHuntActiveFragment : Fragment(), EasyPermissions.PermissionCallbacks,
    TextClueSolveDialogFragment.TextClueSolveDialogListener {

    /**
     * Lazily initialize our [TreasureHuntActiveViewModel].
     */
    private lateinit var viewModel: TreasureHuntActiveViewModel

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTreasureHuntActiveBinding.inflate(inflater)
        val application = requireNotNull(activity).application

        // Initialize the viewmodel
        val treasureHunt = TreasureHuntActiveFragmentArgs.fromBundle(arguments!!).selectedTreasureHunt
        val viewModelFactory = TreasureHuntActiveViewModelFactory(treasureHunt, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TreasureHuntActiveViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Initialize the location services client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as Activity)

        val clueAdapter = ClueAdapter(context!!, object : ClueAdapterListener {
            override fun clueTypeOnClick(type: ClueType) {
                // TODO: make this a dialog
                Toast.makeText(context, "Clicked Type: ${type.name}", Toast.LENGTH_SHORT).show()
            }

            override fun clueSolveOnClick(clue: Clue) {
                // This should be tracked in the viewmodel
                viewModel.setCurrentClue(clue)
                when (clue.state) {
                    ClueState.ACTIVE -> {
                        when (clue.type) {
                            ClueType.Photo -> onSolveCameraClue()
                            ClueType.Location -> onSolveLocationClue()
                            ClueType.Text -> onSolveTextClue()
                        }
                    }
                    ClueState.COMPLETED -> { }
                    ClueState.LOCKED -> {
                        Toast.makeText(context, "You must complete the previous clues before this one is unlocked", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun clueHintOnClick(hint: String) {
                // TODO: make this a dialog
                Toast.makeText(context, "Clicked Hint: $hint", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.clues.observe(this, Observer {
            clueAdapter.notifyDataSetChanged()
        })

        binding.cluesRecyclerview.adapter = clueAdapter

        return binding.root
    }

    private fun onSolveLocationClue() {
        Timber.i("Attempting to navigate to camera clue solver.")
        if (EasyPermissions.hasPermissions(context!!, Manifest.permission.ACCESS_FINE_LOCATION)) {
            checkLocationAgainstCurrentClue()
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, RequestCodes.PERMISSIONS_RC_LOCATION.code,
                    Manifest.permission.ACCESS_FINE_LOCATION).build()
            )
        }
    }

    private fun onSolveTextClue() {
        val clueDialog = TextClueSolveDialogFragment(viewModel.currentActiveClue.value!!)
        clueDialog.setTargetFragment(this, 0)
        fragmentManager?.let { clueDialog.show(it, "clue_text_solve") }
    }

    @SuppressLint("MissingPermission")
    private fun checkLocationAgainstCurrentClue(){
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                     if (viewModel.attemptLocationSolve(location)) {
                         Toast.makeText(context, "Clue Solved!!", Toast.LENGTH_SHORT).show()
                     } else {
                         Toast.makeText(context, "Clue Failed! Location is NOT within accepted radius!", Toast.LENGTH_SHORT).show()
                     }
                } else {
                    Toast.makeText(context, "Unable to get current location", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onSolveCameraClue() {
        Timber.i("Attempting to navigate to camera clue solver.")
        if (EasyPermissions.hasPermissions(context!!, Manifest.permission.CAMERA)) {
            navigateToCameraForClue()
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, RequestCodes.PERMISSIONS_RC_CAMERA.code,
                    Manifest.permission.CAMERA).build()
            )
        }
    }

    private fun navigateToCameraForClue() {
        findNavController()
            .navigate(
                TreasureHuntActiveFragmentDirections
                    .actionTreasureHuntActiveFragmentToCameraFragment(viewModel.currentActiveClue.value!!)
            )
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
                navigateToCameraForClue()
            }
            RequestCodes.PERMISSIONS_RC_LOCATION.code -> {
                checkLocationAgainstCurrentClue()
            }
        }
    }

    override fun onClueAnswerSubmit(answer: String) {
        Toast.makeText(context, "Answered: $answer", Toast.LENGTH_SHORT).show()
    }

    override fun onClueAnswerCancel(dialog: DialogFragment) {
        Toast.makeText(context, "Clicked cancel!", Toast.LENGTH_SHORT).show()
    }
}
