package com.orienteer.treasurecreate

import android.Manifest
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.orienteer.R
import com.orienteer.databinding.FragmentClueLocationCreateBinding
import com.orienteer.models.RequestCodes
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import timber.log.Timber

class ClueTypeLocationCreateFragment : Fragment(),
    OnMapReadyCallback,
    EasyPermissions.PermissionCallbacks {
    private val viewModel: ClueTypeLocationViewModel by lazy {
        ViewModelProviders.of(this).get(ClueTypeLocationViewModel::class.java)
    }
    private lateinit var _fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("Creating fragment view!")
        val binding = FragmentClueLocationCreateBinding.inflate(inflater)

        // Initialize the location services client
        _fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as Activity)

        binding.viewmodel = viewModel

        // Set map callback on the mapView
        val mapFragment = childFragmentManager.findFragmentById(R.id.clueTypeLocationMapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        binding.locationClueConfirmation.setOnClickListener {
            Toast.makeText(context, "Clue saved!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onMapReady(map: GoogleMap?) {
        Timber.i("MAP IS READY POG")
        // Check permissions
        if (!EasyPermissions.hasPermissions(context!!, Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, RequestCodes.PERMISSIONS_RC_LOCATION.code,
                    Manifest.permission.ACCESS_FINE_LOCATION).build()
            )
        }

        // Set the map in the viewModel
        viewModel.setMap(map!!)

        // Get current location
        getDeviceLocation()
    }

    /**
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    private fun getDeviceLocation() {
        try {
            val locationResult = _fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(activity as Activity) { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    viewModel.setLastKnownLocation(task.result as Location)
                    viewModel.updateMap()
                } else {
                    Timber.d("Current location is null. Using defaults.")
                    Timber.e("Exception: ${task.exception}")
                    viewModel.resetMap()
                }
            }

        } catch (e: SecurityException) {
            Timber.e("Exception: ${e.message}")
        }

    }

    // Override the onresult callback to decorate the easy permissions one
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            RequestCodes.PERMISSIONS_RC_LOCATION.code -> {
                Timber.i("Refreshing the map")
            }
        }
    }

}