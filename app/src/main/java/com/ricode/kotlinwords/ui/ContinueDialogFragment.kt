package com.ricode.kotlinwords.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ricode.kotlinwords.R

class ContinueDialogFragment: DialogFragment() {

    @SuppressLint("InflateParams")
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
        dialog.setOnKeyListener {_, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
                dismiss()
            }
            true
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

}