package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ricode.kotlinwords.R
import kotlinx.android.synthetic.main.fragment_start.*


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_start, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_learn.setOnClickListener { v ->
            val navController = v.findNavController()
            navController.navigate(R.id.action_startFragment_to_learnFragment)
        }

        button_test.setOnClickListener { v ->
            val navController = v.findNavController()
            navController.navigate(R.id.action_startFragment_to_testFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_start, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                navigateToSettings()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToSettings() {
        findNavController().navigate(R.id.action_startFragment_to_settingsFragment)
    }


}
