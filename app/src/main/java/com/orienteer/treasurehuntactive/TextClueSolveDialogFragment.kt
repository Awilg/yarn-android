package com.orienteer.treasurehuntactive

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.orienteer.R
import com.orienteer.databinding.DialogClueTextSolveBinding
import com.orienteer.models.Clue

class TextClueSolveDialogFragment(activeClue : Clue) : DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: TextClueSolveDialogListener

    internal var clue = activeClue

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val binding = DialogClueTextSolveBinding.inflate(LayoutInflater.from(context))
            binding.clue = clue

            builder.setView(binding.root)
                .setPositiveButton(R.string.submit) { _, _ ->
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    listener.onDialogNegativeClick(this)
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /*
     * The activity/fragment that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    interface TextClueSolveDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    /*
     *  Check to make sure that the fragment that creates the dialog implements the callback methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            listener = targetFragment as TextClueSolveDialogListener
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("Calling Fragment must implement TextClueSolveDialogListener")
        }
    }
}