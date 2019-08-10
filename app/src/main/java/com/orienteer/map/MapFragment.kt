package com.orienteer.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.orienteer.R
import com.orienteer.core.OnSnapPositionChangeListener
import com.orienteer.core.SnapOnScrollListener
import com.orienteer.core.attachSnapHelperWithListener
import com.orienteer.databinding.CardTreasureHuntBinding
import com.orienteer.databinding.FragmentMapBinding
import com.orienteer.models.TreasureHunt
import com.orienteer.treasurehunts.TreasureHuntsAdapter

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var _locationCallback: LocationCallback
    private lateinit var _fusedLocationProviderClient: FusedLocationProviderClient

    /**
     * Lazily initialize our [MapViewModel].
     */
    private val viewModel: MapViewModel by lazy {
        ViewModelProviders.of(this).get(MapViewModel::class.java)
    }

    private lateinit var _binding: FragmentMapBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("MapFragment", "Creating fragment view!")
        _binding = FragmentMapBinding.inflate(inflater)

        // Giving the binding access to the MapViewModel
        _binding.viewModel = viewModel

        // Initialize the location services client
        _fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as Activity)

        // Set map callback on the mapView
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // Set up the floating action button
        _binding.fab.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapDestinationToTreasureCreateDestination())
        }

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        _binding.lifecycleOwner = this

        // Create the location callback
        _locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){

                    Log.i("MapFragment", "location update $location")
                    // Update the Map UI
                    // Draw moving dot?
                }
            }
        }

        // Create the adapter that will populate the recycler view
        _binding.treasureHuntsCardsMap.adapter = TreasureHuntsAdapter(TreasureHuntsAdapter.OnClickListener {
            Toast.makeText(context, "Clicked treasure hunt ${it.name}!", Toast.LENGTH_LONG).show()
        })

        _binding.treasureHuntsCardsMap.attachSnapHelperWithListener(
            PagerSnapHelper(),
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE,
            object : OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    val adapter = _binding.treasureHuntsCardsMap.adapter as TreasureHuntsAdapter
                    val hunt = adapter.getItem(position)
                    viewModel.moveMapToLocation(hunt.location)
                }
            }
        )

        return _binding.root
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(map: GoogleMap) {
        // Set the map in the viewModel
        viewModel.setMap(map)

        // Check Permissions
        getLocationPermission()

        // Update the Map UI
        viewModel.updateLocationUI()

        // Get Device Location
        getDeviceLocation()

//        // Allow for interval updating
//        startLocationUpdates()
    }

    /**
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.setLocationPermissionGranted(true)
        } else {
            ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                viewModel.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        viewModel.setLocationPermissionGranted(false)
        when (requestCode) {
            viewModel.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.setLocationPermissionGranted(true)
                }
            }
        }
        viewModel.updateLocationUI()
        getDeviceLocation()
    }


    /**
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    private fun getDeviceLocation() {
        try {
            if (viewModel.locationPermissionGranted.value!!) {
                val locationResult = _fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(activity as Activity) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        viewModel.setLastKnownLocation(task.result as Location)
                        viewModel.updateMap()
                    } else {
                        Log.d("MapViewModel", "Current location is null. Using defaults.")
                        Log.e("MapViewModel", "Exception: %s", task.exception)
                        viewModel.resetMap()
                        viewModel.disableMyLocationButtonEnabled()
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("MapFragment", "Exception: ${e.message}")
        }

    }

    override fun onResume() {
        super.onResume()
        if (viewModel.locationPermissionGranted.value!!) startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        // Check Permissions
        getLocationPermission()

        // Build location request

        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        _fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            _locationCallback,
            null /* Looper */)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        _fusedLocationProviderClient.removeLocationUpdates(_locationCallback)
    }

}