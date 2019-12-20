package com.orienteer.models

enum class RequestCodes(val code: Int) {
    PERMISSIONS_RC_CAMERA(100),
    PERMISSIONS_RC_LOCATION(101),
    RC_CAPTURE_IMAGE(200),
    RC_UPLOAD_IMAGE(201)
}