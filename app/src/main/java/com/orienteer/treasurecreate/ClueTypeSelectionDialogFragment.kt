package com.orienteer.treasurecreate

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.orienteer.R
import com.orienteer.models.ClueType
import kotlinx.android.synthetic.main.dialog_clue_type_selection.view.waypoint_clue_type
import kotlinx.android.synthetic.main.dialog_clue_type_selection.view.image_clue_type
import kotlinx.android.synthetic.main.dialog_clue_type_selection.view.text_clue_type
import kotlinx.android.synthetic.main.dialog_clue_type_selection.view.treasure_clue_type

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
            val view =
                LayoutInflater.from(context).inflate(R.layout.dialog_clue_type_selection, null)

            // Emit the correct clue type for the image selections
            view.waypoint_clue_type.setOnClickListener {
                listener.onClueTypeSelected(ClueType.Location)
            }
            view.image_clue_type.setOnClickListener {
                listener.onClueTypeSelected(ClueType.Photo)
            }
            view.text_clue_type.setOnClickListener {
                listener.onClueTypeSelected(ClueType.Text)
            }
            view.treasure_clue_type.setOnClickListener {
                listener.onClueTypeSelected(ClueType.Treasure)
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