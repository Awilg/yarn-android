package com.orienteer.treasurecreate

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orienteer.R
import com.orienteer.Util.DEFAULT_TREASURE_CREATE_ZOOM
import com.orienteer.Util.PermissionsUtil
import com.orienteer.databinding.FragmentTreasureCreateBinding


class TreasureCreateFragment : Fragment(), OnMapReadyCallback {

    /**
     * Lazily initialize our [TreasureCreateViewModel].
     */
    private val viewModel: TreasureCreateViewModel by lazy {
        ViewModelProviders.of(this).get(TreasureCreateViewModel::class.java)
    }

    /**
     * The client needed to get location for the Treasure hunt
     */
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * Overriding the onCreateView to use the databinding inflate
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTreasureCreateBinding.inflate(inflater)

        // To use the binding with databinding you need to explicitly give the binding a reference to it.
        binding.viewModel = viewModel

        // Set up location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)

        // Set up the action bar
        val toolbar: Toolbar = binding.toolbarInclude?.toolbar!!
        setSupportActionBar(toolbar)

        // Set up the map callback on the fragment. Must us the childFragmentManager since the map fragment
        // is nested inside this current fragment. This also MUST BE A SupportMapFragment for it to be discoverable
        // via the fragmentManager. This is stupid.
        // TODO (James): Research why this fragment is not populated in the databinding for the layout
        val mapFragment = childFragmentManager.findFragmentById(R.id.create_treasure_hunt_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        viewModel.navigateToSuccessScreen.observe(this, Observer {
            if (it == true) {
                Log.i("TreasureCreateFragment", "navigating to the success screen!")
                viewModel.setTreasureHuntName(binding.treasureHuntName.text.toString())
                Toast.makeText(context, "Treasure Hunt ${viewModel.treasureHuntName.value} created!", Toast.LENGTH_LONG)
                    .show()
                viewModel.doneNavigating()
            }
        })
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

    private fun drawMap(map: GoogleMap, location: Location): Circle? {
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
        map.addCircle(
            CircleOptions()
                .center(LatLng(location.latitude, location.longitude))
                .radius(1000.0)
                .fillColor(0x40FFD700)
                .strokeColor(0x40FFD700)
        )
        map.addCircle(
            CircleOptions()
                .center(LatLng(location.latitude, location.longitude))
                .radius(500.0)
                .fillColor(0x40a8ff00)
                .strokeColor(0x40a8ff00)
        )
        map.addCircle(
            CircleOptions()
                .center(LatLng(location.latitude, location.longitude))
                .radius(250.0)
                .fillColor(0x40ff5700)
                .strokeColor(0x40ff5700)
        )
        return map.addCircle(
            CircleOptions()
                .center(LatLng(location.latitude, location.longitude))
                .radius(50.0)
                .fillColor(0x40ff0029)
                .strokeColor(0x40ff0029)
        )
    }
}