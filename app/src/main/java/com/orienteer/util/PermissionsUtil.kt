package com.orienteer.util

import android.app.Activity

enum class PermissionsUtil(val perm: Int) {
    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION(1)
}

val permissionMap = HashMap<Activity, Boolean>()