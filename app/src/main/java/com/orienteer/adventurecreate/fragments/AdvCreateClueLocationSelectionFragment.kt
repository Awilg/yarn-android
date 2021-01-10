package com.orienteer.adventurecreate.fragments

import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.orienteer.R
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.databinding.FragmentAdvcreateCluesLocationSelectionBinding
import com.orienteer.models.RequestCodes
import com.orienteer.util.EasyPermissionsFragment
import com.orienteer.util.checkFineLocation
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

class AdvCreateClueLocationSelectionFragment : EasyPermissionsFragment(), MavericksView,
    OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateCluesLocationSelectionBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateCluesLocationSelectionBinding.inflate(inflater)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity as Activity)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            this.findNavController().navigate(
                AdvCreateClueLocationFragmentDirections.actionAdvCreateClueLocationFragmentToAdvCreateClueLocationSelectionFragment()
            )
        }
        return binding.root
    }

    override fun invalidate() {}

    override fun onMapReady(map: GoogleMap?) {
        checkFineLocation(requireContext(), this)
        viewModel.setCurrentClueLocationMap(map)
        getDeviceLocation()
    }

    /**
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    private fun getDeviceLocation() {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(activity as Activity) { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    viewModel.setLastKnownLocation(task.result as Location)
                    viewModel.updateClueLocationMap()
                } else {
                    Timber.d("Current location is null. Using defaults.")
                    Timber.e("Exception: ${task.exception}")
                    viewModel.resetClueLocationMap()
                }
            }

        } catch (e: SecurityException) {
            Timber.e("Exception: ${e.message}")
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            RequestCodes.PERMISSIONS_RC_LOCATION.code -> {
                Timber.i("TEST: Refreshing the map")
            }
        }
    }
}