package com.orienteer.treasurehuntactive

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.orienteer.R
import com.orienteer.core.ClueAdapter
import com.orienteer.core.ClueAdapterListener
import com.orienteer.databinding.FragmentTreasureHuntActiveBinding
import com.orienteer.models.Clue
import com.orienteer.models.ClueState
import com.orienteer.models.ClueType
import com.orienteer.models.RequestCodes
import com.orienteer.util.PermissionsUtil
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import timber.log.Timber

class TreasureHuntActiveFragment : Fragment(), EasyPermissions.PermissionCallbacks,
    OnMapReadyCallback,
    TextClueSolveDialogFragment.TextClueSolveDialogListener {

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

        // Set map callback on the mapView
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

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

        viewModel.navigateToCompletedScreen.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    TreasureHuntActiveFragmentDirections
                        .actionTreasureHuntActiveFragmentToAdventureCompletedFragment()
                )
                viewModel.doneNavigating()
            }
        })

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet)
        bottomSheetBehavior.isFitToContents = true
        bottomSheetBehavior.peekHeight = convertDpToPixel(370)

        binding.cluesRecyclerview.adapter = clueAdapter


        val appbar = binding.appbarLayout
        appbar.background.alpha = 0
        // This is to remove the elevation shadow while maintaining the elevation to draw
        // on top of the other views
        appbar.outlineProvider = null
        appbar.fitsSystemWindows = true

        val mToolbar = binding.myToolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(mToolbar)
        setHasOptionsMenu(true)
        mToolbar.title = null
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        mToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        // Inflate the menu; this adds items to the action bar if it is present.
        activity!!.menuInflater.inflate(R.menu.app_bar_active, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_abandon -> {
                Toast.makeText(context, "Abandoned!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_report -> {
                Toast.makeText(context, "Reported!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                Toast.makeText(context, "Settings!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

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

    private fun convertDpToPixel(dp: Int): Int {
        return dp * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
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
                PermissionsUtil.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION.perm
            )
        }
    }

    /**
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    private fun getDeviceLocation() {
        try {
            if (viewModel.locationPermissionGranted.value!!) {
                val locationResult = fusedLocationProviderClient.lastLocation
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

    override fun onTextClueAnswerSubmit(answer: String) {
        viewModel.attemptTextSolve(answer)
    }

    override fun onTextClueAnswerCancel(dialog: DialogFragment) {
        Toast.makeText(context, "Clicked cancel!", Toast.LENGTH_SHORT).show()
    }
}
