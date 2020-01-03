package com.ricode.cardwords.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ricode.cardwords.R

class OneButtonDialog(private val dialogText: String): DialogFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = activity?.layoutInflater
        val v = inflater?.inflate(R.layout.one_button_dialog_layout, null)
        val singleButton = v?.findViewById<Button>(R.id.button_neutral)
        singleButton?.setOnClickListener {
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
            dismiss()
        }
        val textDialog = v?.findViewById<TextView>(R.id.dialog_text)
        textDialog?.text = dialogText
        builder.setView(v)
        return builder.create()
    }
}