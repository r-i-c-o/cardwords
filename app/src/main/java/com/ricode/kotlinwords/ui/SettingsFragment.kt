package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricode.kotlinwords.R

// set up settings
class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        val list = view.findViewById<RecyclerView>(R.id.settings_list)
        list.adapter = SettingsAdapter()
        list.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    private class SettingsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class TextNumberRow(itemView: View): RecyclerView.ViewHolder(itemView) {}

    class TextSwitchRow(itemView: View): RecyclerView.ViewHolder(itemView) {}

    class TextRow(itemView: View): RecyclerView.ViewHolder(itemView) {}
}
