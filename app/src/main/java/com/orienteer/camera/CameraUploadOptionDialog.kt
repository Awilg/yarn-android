package com.orienteer.camera

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.orienteer.R
import com.orienteer.models.RequestCodes
import kotlinx.android.synthetic.main.dialog_photo_upload_option.view.*
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraUploadOptionDialog : DialogFragment(), EasyPermissions.PermissionCallbacks {
    internal lateinit var listener: CameraUploadOptionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view =
                LayoutInflater.from(context).inflate(R.layout.dialog_photo_upload_option, null)

            view.gallery_button.setOnClickListener {
                Toast.makeText(context, "Clicked gallery", Toast.LENGTH_SHORT).show()

                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"

                    // Optionally, specify a URI for the file that should appear in the
                    // system file picker when it loads.
                    // putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
                }

                startActivityForResult(intent, RC_UPLOAD_IMG)
            }
            view.camera_button.setOnClickListener {
                checkPermissionsAndPerformCamera()
            }
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /*
     * The activity/fragment that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    interface CameraUploadOptionListener

    /*
     *  Check to make sure that the fragment that creates the dialog implements the callback methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            listener = targetFragment as CameraUploadOptionListener
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("Calling Fragment must implement ClueTypeSelectionListener")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RC_CAPTURE_IMG -> Toast.makeText(
                    context,
                    "IMAGE CAPTURED",
                    Toast.LENGTH_SHORT
                ).show()
                RC_UPLOAD_IMG -> Toast.makeText(
                    context,
                    "IMAGE UPLOADED",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    /**
     * Check for the camera permission before firing the camera intent.
     */
    private fun checkPermissionsAndPerformCamera() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            sendCameraIntent()
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.CAMERA),
                RequestCodes.PERMISSIONS_RC_CAMERA.code
            )
        }
    }

    /**
     * Sends the camera intent. Defines a URI for the photo to be stored at.
     */
    private fun sendCameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            // Create the File where the photo should go
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                Timber.e("Exception creating image file")
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also { file ->

                val photoURI: Uri = FileProvider.getUriForFile(
                    context!!,
                    "com.orienteer.fileprovider",
                    file
                )
                this.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(this, RC_CAPTURE_IMG)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            RequestCodes.PERMISSIONS_RC_CAMERA.code -> {
                sendCameraIntent()
            }
        }
    }

    companion object {
        private const val RC_UPLOAD_IMG = 123
        private const val RC_CAPTURE_IMG = 124
    }
}