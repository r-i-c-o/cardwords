package com.ricode.kotlinwords.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ricode.kotlinwords.R

class ContinueDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = activity?.layoutInflater
        val v = inflater?.inflate(R.layout.continue_dialog_layout, null)
        builder.setView(v)

        return builder.create()
    }
}