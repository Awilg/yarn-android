package com.orienteer.camera

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.orienteer.R
import kotlinx.android.synthetic.main.dialog_photo_upload_option.view.*

class CameraUploadOptionDialog : DialogFragment() {
    internal lateinit var listener: CameraUploadOptionListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val view =
                LayoutInflater.from(context).inflate(R.layout.dialog_photo_upload_option, null)

            // Emit the correct clue type for the image selections
            view.gallery_button.setOnClickListener {
                Toast.makeText(context, "Clicked gallery", Toast.LENGTH_SHORT).show()

                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"

                    // Optionally, specify a URI for the file that should appear in the
                    // system file picker when it loads.
                    //putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
                }

                startActivityForResult(intent, RC_UPLOAD_IMG)
            }
            view.camera_button.setOnClickListener {
                Toast.makeText(context, "Clicked camera", Toast.LENGTH_SHORT).show()
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

        if (requestCode == RC_UPLOAD_IMG && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Toast.makeText(context, "ON RESULT OVERRIDE", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private const val RC_UPLOAD_IMG = 123
    }
}