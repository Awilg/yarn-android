package com.orienteer.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.orienteer.R
import com.orienteer.core.OnSnapPositionChangeListener
import com.orienteer.core.SnapOnScrollListener
import com.orienteer.core.attachSnapHelperWithListener
import com.orienteer.databinding.FragmentMapBinding
import com.orienteer.treasurehunts.TreasureHuntsAdapter
import timber.log.Timber

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("Creating fragment view!")
        _binding = FragmentMapBinding.inflate(inflater)

        // Giving the binding access to the MapViewModel
        _binding.viewModel = viewModel

        // Initialize the location services client
        _fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity as Activity)

        // Set map callback on the mapView
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


//        // Set up the floating action button
//        _binding.fab.setOnClickListener {
//            findNavController().navigate(MapFragmentDirections.actionMapDestinationToTreasureCreateDestination())
//        }

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        _binding.lifecycleOwner = this

        // Create the location callback
        _locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {

                    Timber.i("location update $location")
                    // Update the Map UI
                    // Draw moving dot?
                }
            }
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(_binding.standardBottomSheet)
        bottomSheetBehavior.isFitToContents = true
        bottomSheetBehavior.peekHeight = convertDpToPixel(370)

        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Do something for slide offset
            }
        }
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)

        // Move the "My location" button to the bottom right
        val locationButton = ((mapFragment?.view?.findViewById(Integer.parseInt("1")) as View)
            .parent as View).findViewById(Integer.parseInt("2")) as View

        // and next place it, for example, on bottom right (as Google Maps app)
        val rlp: RelativeLayout.LayoutParams =
            locationButton.layoutParams as RelativeLayout.LayoutParams
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.setMargins(0, 0, 30, 30)


        // Create the adapter that will populate the recycler view and updates the livedata
        // in the viewmodel to determine where to navigate.
        _binding.treasureHuntsCardsMap.adapter =
            TreasureHuntsAdapter(TreasureHuntsAdapter.OnClickListener {
                viewModel.displayTreasureHuntDetails(it)
            }, useFeaturedBinding = true)

        // Adds a listener that is aware of "swipe" card changes to the underlying RecyclerView
        // Snaps cards to the screen so there's only one ever fully on screen and moves the map to
        // the location of the hunt currently on screen.
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

        // Observe the navigateToSelectedTreasureHunt LiveData and Navigate when it isn't null
        // After navigating, call doneNavigatingToSelectedTreasureHunt() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedAdventure.observe(this, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(
                    MapFragmentDirections.actionMapDestinationToTreasureHuntDetail(it)
                )

                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.doneNavigatingToSelectedTreasureHunt()
            }
        })

        // Draw the markers for the hunts when the list is updated
        viewModel.treasureHuntsNearby.observe(this, Observer { adventureList ->
            adventureList.forEach {adv ->
                viewModel.addMarkerForAdventure(adv)
            }
        })

        return _binding.root
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.locationPermissionGranted.value!!) startLocationUpdates()
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
        // Adjust map style for current theme
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                map.setMapStyle(MapStyleOptions(resources.getString(R.string.google_maps_night_mode)))
            }
        }

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
                        Timber.d("Current location is null. Using defaults.")
                        Timber.e("Exception: ${task.exception}")
                        viewModel.resetMap()
                        viewModel.disableMyLocationButtonEnabled()
                    }
                }
            }
        } catch (e: SecurityException) {
            Timber.e("Exception: ${e.message}")
        }

    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        // Check Permissions
        getLocationPermission()

        // Build location request

        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        _fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            _locationCallback,
            null /* Looper */
        )
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Int): Int {
        return dp * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Int): Int {
        return px / (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }


    private fun stopLocationUpdates() {
        _fusedLocationProviderClient.removeLocationUpdates(_locationCallback)
    }

}