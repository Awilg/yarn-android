package com.orienteer.treasurecreate

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.orienteer.R

class ClueTypeSelectionDialogFragment : DialogFragment() {
    internal lateinit var listener: ClueTypeSelectionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val view =LayoutInflater.from(context).inflate(R.layout.dialog_clue_type_selection, null)

            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /*
     * The activity/fragment that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    interface ClueTypeSelectionListener {
        fun onClueTypeSelected(answer: String)
        fun onClueTypeSelectionCancel(dialog: DialogFragment)
    }

    /*
     *  Check to make sure that the fragment that creates the dialog implements the callback methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            listener = targetFragment as ClueTypeSelectionListener
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("Calling Fragment must implement TextClueSolveDialogListener")
        }
    }
}