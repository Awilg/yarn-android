package com.orienteer.Util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng

val DEFAULT_LOCATION = LatLng(-33.8523341, 151.2106085)
val DEFAULT_ZOOM = 15F
val DEFAULT_TREASURE_CREATE_ZOOM = 14F

class LocationUtil {


    /**
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    private fun getLocationPermission(context: Context, activity : Activity) {
        if (ContextCompat.checkSelfPermission(
                context!!.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PermissionsUtil.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION.perm
            )
        }
    }


}