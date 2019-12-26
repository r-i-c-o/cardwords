package com.ricode.kotlinwords.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ricode.kotlinwords.R

class NumPickerDialog(val titleText: String, var numberOfSmth: Int): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        return builder.create()
    }


}