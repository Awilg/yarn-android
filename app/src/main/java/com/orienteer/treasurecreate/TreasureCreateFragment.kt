package com.orienteer.treasurecreate

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orienteer.R
import com.orienteer.databinding.FragmentTreasureCreateBinding
import com.orienteer.models.ClueTextCreate
import com.orienteer.models.ClueType
import com.orienteer.util.PermissionsUtil
import com.orienteer.util.hideKeyboard
import timber.log.Timber


class TreasureCreateFragment : Fragment(), OnMapReadyCallback,
    ClueTypeSelectionDialogFragment.ClueTypeSelectionListener,
    ClueTypeTextCreateDialogFragment.TextClueCreateDialogListener {

    private lateinit var viewModel: TreasureCreateViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * Overriding the onCreateView to use the databinding inflate
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTreasureCreateBinding.inflate(inflater)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[TreasureCreateViewModel::class.java]
        }!!

        // To use the binding with databinding you need to explicitly give the binding a reference to it.
        binding.viewModel = viewModel

        // Set up location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)

        // Set map callback on the mapView
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.createMapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // Set up observer for the create button
        viewModel.navigateToSuccessScreen.observe(this, Observer {
            if (it == true) {
                hideKeyboard()
                viewModel.setTreasureHuntName(binding.adventureCreateContent.adventureNameText.toString())
                viewModel.setTreasureHuntDescription(binding.adventureCreateContent.adventureDescriptionText.text.toString())
                viewModel.saveAdventure()
                viewModel.doneNavigating()
            }
        })

        // Set up observer for the preview button
        viewModel.navigateToPreview.observe(this, Observer {
            if (it == true) {
                Timber.i("navigating to the preview screen!")
                hideKeyboard()
                Toast.makeText(context, "Preview!", Toast.LENGTH_SHORT)
                    .show()
                viewModel.doneNavigating()
            }
        })

        // Set up observer for the add clue button
        viewModel.navigateAddClue.observe(this, Observer {
            if (it == true) {
                hideKeyboard()
                showClueTypeSelection()
                viewModel.doneNavigating()
            }
        })

        // Set up the adapter for the clues
        val clueAdapter = AdventureClueCreateAdapter()

        viewModel.clues.observe(this, Observer {
            clueAdapter.submitList(it?.toMutableList())
        })

        binding.adventureCreateContent.cluesSection.adapter = clueAdapter

        return binding.root
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionsUtil.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION.perm -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO: refresh mapView
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(map: GoogleMap) {
        // Check permissions. This should be already handled by the main activity but just to be safe...
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    // Got last known location. In some rare situations this can be null.
                    // Set the map's camera position to the current location of the device.
                    viewModel.setTreasureHuntLocation(location)
                    drawMap(map, location)
                }
            }

        } else {
            requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PermissionsUtil.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION.perm
            )
        }
    }

    private fun drawMap(map: GoogleMap, location: Location) {
        map.addMarker(
            MarkerOptions()
                .position(LatLng(location.latitude, location.longitude))
                .title("Treasure Location!")
        )
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location.latitude,
                    location.longitude
                ), DEFAULT_TREASURE_CREATE_ZOOM
            )
        )
    }

    private fun showClueTypeSelection() {
        val clueDialog = ClueTypeSelectionDialogFragment()
        clueDialog.setTargetFragment(this, 0)
        fragmentManager?.let { clueDialog.show(it, "clue_type_selection") }
    }

    override fun onClueTypeSelected(type: ClueType) {
        Toast.makeText(context, "Selected a ${type.name} clue!", Toast.LENGTH_SHORT).show()
        when (type) {
            ClueType.Location -> this.findNavController().navigate(
                TreasureCreateFragmentDirections
                    .actionTreasureCreateFragmentToClueTypeLocationCreateFragment()
            )
            ClueType.Photo -> this.findNavController().navigate(
                TreasureCreateFragmentDirections
                    .actionTreasureCreateFragmentToClueTypePhotoCreateFragment()
            )
            ClueType.Text -> {
                val textClueCreateDialog = ClueTypeTextCreateDialogFragment()
                textClueCreateDialog.setTargetFragment(this, 0)
                fragmentManager?.let { textClueCreateDialog.show(it, "clue_text_create") }
            }
            ClueType.Treasure -> this.findNavController().navigate(
                TreasureCreateFragmentDirections
                    .actionTreasureCreateFragmentToClueTypeTreasureCreateFragment()
            )
        }
    }

    override fun onClueTypeSelectionCancel(dialog: DialogFragment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextClueCreateSubmit(clueToCreate: ClueTextCreate) {
        viewModel.addTextClue(clueToCreate)
    }

    override fun onTextClueCreateCancel(dialog: DialogFragment) {
        Toast.makeText(context, "Text clue create cancelled!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val DEFAULT_TREASURE_CREATE_ZOOM = 14F
    }
}