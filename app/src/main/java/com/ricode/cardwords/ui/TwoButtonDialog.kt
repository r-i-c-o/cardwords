package com.ricode.cardwords.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ricode.cardwords.R

class TwoButtonDialog(private val dialogText: String): DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = activity?.layoutInflater
        val v = inflater?.inflate(R.layout.two_button_dialog_layout, null)
        val positiveButton = v?.findViewById<Button>(R.id.button_dialog_positive)
        positiveButton?.setOnClickListener {
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
            dismiss()
        }
        val negativeButton = v?.findViewById<Button>(R.id.button_dialog_negative)
        negativeButton?.setOnClickListener {
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
            dismiss()
        }
        val textDialog = v?.findViewById<TextView>(R.id.dialog_text)
        textDialog?.text = dialogText
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
        return dialog
    }

}