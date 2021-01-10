package com.orienteer.util

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import com.orienteer.models.RequestCodes
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

fun checkFineLocation(context: Context, fragment: Fragment) {
    if (!EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) {
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(
                fragment, RequestCodes.PERMISSIONS_RC_LOCATION.code,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).build()
        )
    }
}