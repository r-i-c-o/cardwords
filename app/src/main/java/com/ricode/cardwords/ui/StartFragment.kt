package com.ricode.cardwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ricode.cardwords.R
import com.ricode.cardwords.data.PackHelper
import com.ricode.cardwords.data.PackNames
import com.ricode.cardwords.data.WordManager
import com.ricode.cardwords.files.AppSettings
import com.ricode.cardwords.files.AssetHelper
import kotlinx.android.synthetic.main.fragment_start.*


class StartFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val assetHelper = AssetHelper(requireContext())
        if (!AppSettings(requireContext()).getRebuilt()) {
            findNavController().navigate(R.id.action_startFragment_to_rebuildingFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pack_name.text = PackHelper(requireContext()).currentPackName
        pack_progress.text = getString(R.string.progress_title, 0)

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
                val fm = parentFragmentManager
                dialogFragment.show(fm, "StartFragment")
            }
        }
        button_settings.setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_startFragment_to_settingsFragment)
        }
    }

}
