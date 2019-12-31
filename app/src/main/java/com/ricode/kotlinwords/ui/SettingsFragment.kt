package com.ricode.kotlinwords.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.files.AppSettings
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(), OnItemClickListener {

    val VIEW_EMPTY = 0
    val VIEW_NUM = 1
    val VIEW_SWITCH = 2

    var rowCount = 0
    val rowWordNumber = rowCount++
    val rowWordRepeats = rowCount++
    val rowDivider = rowCount++
    val rowDarkTheme = rowCount++
    //val rowAppVersion = rowCount++

    private lateinit var mSettings: AppSettings
    private lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSettings = AppSettings(requireContext())
    }

    override fun onItemClick(position: Int) {
        when (position) {
            rowWordNumber -> {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                val array = Array(8) { i-> (10 + 5*i).toString()}
                dialogBuilder.setTitle(getString(R.string.settings_number_of_words))
                dialogBuilder.setItems(array) { _, which ->
                    mSettings.setNumberOfWords(array[which].toInt())
                    updateUI()
                }
                val dialog = dialogBuilder.create()
                dialog.show()
            }
            rowWordRepeats -> {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                val array = Array(4) { i-> (i + 2).toString()}
                dialogBuilder.setTitle(getString(R.string.settings_tries))
                dialogBuilder.setItems(array) { _, which ->
                    mSettings.setNumberOfTries(array[which].toInt())
                    updateUI()
                }
                val dialog = dialogBuilder.create()
                dialog.show()
            }
        }
    }

    override fun onSwitchChanged(position: Int, isChecked: Boolean) {
        when (position) {
            rowDarkTheme -> {
                mSettings.setDarkMode(isChecked)
                updateUI()
                activity?.recreate()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        list = view.findViewById(R.id.settings_list)
        list.adapter = SettingsAdapter(this)
        list.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    private fun updateUI() {
        list.adapter?.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private inner class SettingsAdapter(val listener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            when (holder.itemViewType) {
                VIEW_NUM -> {
                    val numHolder = holder as TextNumberRow
                    var text = ""
                    var num = 0
                    when (position) {
                        rowWordNumber -> {
                            text = getString(R.string.settings_number_of_words)
                            num = mSettings.getNumberOfWords()
                        }
                        rowWordRepeats -> {
                            text = getString(R.string.settings_tries)
                            num = mSettings.getNumberOfTries()
                        }
                    }
                    numHolder.bind(text, num, listener, position)

                }
                VIEW_SWITCH -> {
                    val switchHolder = holder as TextSwitchRow
                    switchHolder.bind(getString(R.string.settings_darkmode), mSettings.getDarkMode(), listener, position)
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                rowWordNumber, rowWordRepeats -> VIEW_NUM
                rowDarkTheme -> VIEW_SWITCH
                else -> VIEW_EMPTY
            }
        }
    }

    class TextNumberRow(itemView: View): RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.cell_text_num)
        private val number: TextView = itemView.findViewById(R.id.cell_num)
        fun bind(settingText: String, settingNumber: Int, listener: OnItemClickListener, position: Int) {
            text.text = settingText
            number.text = settingNumber.toString()
            itemView.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }

    class TextSwitchRow(itemView: View): RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.cell_text_sw)
        private val switch: Switch = itemView.findViewById(R.id.cell_switch)
        fun bind(settingText: String, bool: Boolean, listener: OnItemClickListener, position: Int) {
            text.text = settingText
            switch.isChecked = bool
            switch.setOnCheckedChangeListener {_, isChecked ->
                listener.onSwitchChanged(position, isChecked)
            }
        }
    }

    class TextRow(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    class EmptyRow(itemView: View): RecyclerView.ViewHolder(itemView)
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onSwitchChanged(position: Int, isChecked: Boolean)
}