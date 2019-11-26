package com.ricode.kotlinwords.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ricode.kotlinwords.R

class ContinueDialogFragment: DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = activity?.layoutInflater
        val v = inflater?.inflate(R.layout.continue_dialog_layout, null)
        val positiveButton = v?.findViewById<Button>(R.id.continue_dialog_positive)
        positiveButton?.setOnClickListener {
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
            dismiss()
        }
        val negativeButton = v?.findViewById<Button>(R.id.continue_dialog_negative)
        negativeButton?.setOnClickListener {
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
            dismiss()
        }
        val dialogText = v?.findViewById<TextView>(R.id.continue_dialog_text)
        dialogText?.text = getString(R.string.continue_dialog_text, 20)
        builder.setView(v)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    /*override fun onClick(v: View?) {
        when(v?.id) {
            R.id.continue_dialog_positive -> targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
            R.id.continue_dialog_negative -> targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
        }
    }*/
}