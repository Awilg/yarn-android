package com.orienteer.treasurecreate

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.orienteer.databinding.DialogClueTypeSelectionBinding
import com.orienteer.models.ClueType

/**
 * This is the dialog that is used to select which [ClueType] of a [Clue] to create while creating
 * a [TreasureHunt].
 */
class ClueTypeSelectionDialogFragment : DialogFragment() {
    internal lateinit var listener: ClueTypeSelectionListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val binding = DialogClueTypeSelectionBinding.inflate(LayoutInflater.from(context), null, false)

            // Emit the correct clue type for the image selections
            binding.waypointClueType.setOnClickListener {
                listener.onClueTypeSelected(ClueType.Location)
                dismiss()
            }
            binding.imageClueType.setOnClickListener {
                listener.onClueTypeSelected(ClueType.Photo)
                dismiss()
            }
            binding.textClueType.setOnClickListener {
                listener.onClueTypeSelected(ClueType.Text)
                dismiss()
            }
            binding.treasureClueType.setOnClickListener {
                listener.onClueTypeSelected(ClueType.Treasure)
                dismiss()
            }
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /*
     * The activity/fragment that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    interface ClueTypeSelectionListener {
        fun onClueTypeSelected(type: ClueType)
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
            throw ClassCastException("Calling Fragment must implement ClueTypeSelectionListener")
        }
    }
}