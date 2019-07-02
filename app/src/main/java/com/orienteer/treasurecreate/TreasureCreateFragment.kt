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
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orienteer.R
import com.orienteer.Util.DEFAULT_ZOOM
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

        // Set up the map callback on the fragment.
        // TODO (James): Research why this fragment is not populated in the databinding for the layout
        val mapFragment = fragmentManager!!
            .findFragmentById(R.id.create_treasure_hunt_map) as SupportMapFragment?
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

    override fun onMapReady(map: GoogleMap?) {
        if (ContextCompat.checkSelfPermission(
                context!!.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    map?.addMarker(
                        MarkerOptions()
                            .position(LatLng(location.latitude, location.longitude))
                            .title("Treasure Location!")
                    )
                    map?.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                location.latitude,
                                location.longitude
                            ), DEFAULT_ZOOM
                        )
                    )
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
}