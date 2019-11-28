package com.orienteer.treasurecreate

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.orienteer.R
import com.orienteer.databinding.DialogClueTextCreateBinding
import com.orienteer.models.ClueTextCreate

class ClueTypeTextCreateDialogFragment : DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: TextClueCreateDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val binding = DialogClueTextCreateBinding.inflate(LayoutInflater.from(context))

            builder.setView(binding.root)
                .setPositiveButton(R.string.submit) { _, _ ->
                    listener.onTextClueCreateSubmit(
                        ClueTextCreate(
                            cluePrompt = binding.clueTextCreateClue.text.toString(),
                            answer = binding.clueTextCreateAnswer.text.toString(),
                            hint_1 = binding.clueTextCreateHint1.text.toString(),
                            hint_2 = binding.clueTextCreateHint2.text.toString(),
                            hint_3 = binding.clueTextCreateHint3.text.toString()
                        )
                    )
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    listener.onTextClueCreateCancel(this)
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
    interface TextClueCreateDialogListener {
        fun onTextClueCreateSubmit(clueToCreate: ClueTextCreate)
        fun onTextClueCreateCancel(dialog: DialogFragment)
    }

    /*
     *  Check to make sure that the fragment that creates the dialog implements the callback methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            listener = targetFragment as TextClueCreateDialogListener
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("Calling Fragment must implement TextClueCreateDialogListener")
        }
    }
}