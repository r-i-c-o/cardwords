package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.data.PackNames
import com.ricode.kotlinwords.data.WordManager
import com.ricode.kotlinwords.files.AssetHelper
import kotlinx.android.synthetic.main.fragment_start.*


class StartFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val assetHelper = AssetHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_learn.setOnClickListener { v ->
            val navController = v.findNavController()
            navController.navigate(R.id.action_startFragment_to_learnFragment)
        }

        button_test.setOnClickListener { v ->
            if (!WordManager(requireContext()).isFileEmpty(PackNames.TEST)) {
                val navController = v.findNavController()
                navController.navigate(R.id.action_startFragment_to_testFragment)
            } else {
                val dialogFragment = OneButtonDialog(getString(R.string.empty_test_dialog_text))
                val fm = fragmentManager
                if (fm != null) dialogFragment.show(fm, "StartFragment")
            }
        }

        button_settings.setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_startFragment_to_settingsFragment)
        }
    }

}
