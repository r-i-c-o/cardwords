package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricode.kotlinwords.R
import kotlinx.android.synthetic.main.fragment_settings.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private class SettingsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val VIEW_EMPTY = 0
        val VIEW_NUM = 1
        val VIEW_SWITCH = 2

        var rowCount = 0
        val rowWordNumber = rowCount++
        val rowWordRepeats = rowCount++
        val rowDarkTheme = rowCount++

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                VIEW_NUM -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_text_num, parent, false)
                    TextNumberRow(view)
                }
                VIEW_SWITCH -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_text_switch, parent, false)
                    TextSwitchRow(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_empty, parent, false)
                    EmptyRow(view)
                }
            }
        }

        override fun getItemCount(): Int {
            return rowCount
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                rowWordNumber, rowWordRepeats -> VIEW_NUM
                rowDarkTheme -> VIEW_SWITCH
                else -> VIEW_EMPTY
            }
        }
    }

    class TextNumberRow(itemView: View): RecyclerView.ViewHolder(itemView) {}

    class TextSwitchRow(itemView: View): RecyclerView.ViewHolder(itemView) {}

    class TextRow(itemView: View): RecyclerView.ViewHolder(itemView) {}

    class EmptyRow(itemView: View): RecyclerView.ViewHolder(itemView) {}
}
