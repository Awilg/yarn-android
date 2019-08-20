package com.orienteer.treasurehuntactive

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.orienteer.R

class TextClueSolveDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_clue_text_solve, null))// Add action buttons
                .setPositiveButton(R.string.submit,
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(context, "Clicked ok!", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(context, "Clicked cancel!", Toast.LENGTH_SHORT).show()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}