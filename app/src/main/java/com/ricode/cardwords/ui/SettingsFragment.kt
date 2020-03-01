package com.ricode.cardwords.ui

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
import com.ricode.cardwords.R
import com.ricode.cardwords.files.AppSettings
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(), OnItemClickListener {

    val VIEW_EMPTY = 0
    val VIEW_TEXT_TEXT = 1
    val VIEW_SWITCH = 2
    val VIEW_TEXT = 3

    var rowCount = 0
    val rowWordNumber = rowCount++
    val rowWordRepeats = rowCount++
    val rowAppearanceSection = rowCount++
    val rowTextSize = rowCount++
    val rowDarkTheme = rowCount++
    val rowVersionSection = rowCount++
    val rowAppVersion = rowCount++

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
                val array = Array(5) { i-> (i + 1).toString()}
                dialogBuilder.setTitle(getString(R.string.settings_tries))
                dialogBuilder.setItems(array) { _, which ->
                    mSettings.setNumberOfTries(array[which].toInt())
                    updateUI()
                }
                val dialog = dialogBuilder.create()
                dialog.show()
            }
            rowTextSize -> {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                val array = arrayOf(
                    getString(R.string.settings_text_size_normal),
                    getString(R.string.settings_text_size_big),
                    getString(R.string.settings_text_size_huge)
                )
                dialogBuilder.setTitle(getString(R.string.settings_text_size))
                dialogBuilder.setItems(array) { _, which ->
                    mSettings.setTextSize(which)
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
                VIEW_TEXT_TEXT -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text_num, parent, false)
                    TextTextRow(view)
                }
                VIEW_SWITCH -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text_switch, parent, false)
                    TextSwitchRow(view)
                }
                VIEW_TEXT -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
                    TextRow(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
                    EmptyRow(view)
                }
            }
        }

        override fun getItemCount(): Int {
            return rowCount
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                VIEW_TEXT_TEXT -> {
                    val numHolder = holder as TextTextRow
                    var title = ""
                    var value = ""
                    when (position) {
                        rowWordNumber -> {
                            title = getString(R.string.settings_number_of_words)
                            value = mSettings.getNumberOfWords().toString()
                        }
                        rowWordRepeats -> {
                            title = getString(R.string.settings_tries)
                            value = mSettings.getNumberOfTries().toString()
                        }
                        rowTextSize -> {
                            title = getString(R.string.settings_text_size)
                            when (mSettings.getTextSize()) {
                                0 -> value = getString(R.string.settings_text_size_normal)
                                1 -> value = getString(R.string.settings_text_size_big)
                                2 -> value = getString(R.string.settings_text_size_huge)
                            }
                        }
                    }
                    numHolder.bind(title, value, listener, position)

                }
                VIEW_SWITCH -> {
                    val switchHolder = holder as TextSwitchRow
                    switchHolder.bind(getString(R.string.settings_darkmode), mSettings.getDarkMode(), listener, position)
                }
                VIEW_TEXT -> {
                    val textHolder = holder as TextRow
                    textHolder.bind(getString(R.string.version_app), getString(R.string.version_app_summary))
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                rowWordNumber, rowWordRepeats, rowTextSize -> VIEW_TEXT_TEXT
                rowDarkTheme -> VIEW_SWITCH
                rowAppVersion -> VIEW_TEXT
                else -> VIEW_EMPTY
            }
        }
    }

    class TextTextRow(itemView: View): RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.cell_text_num)
        private val number: TextView = itemView.findViewById(R.id.cell_num)
        fun bind(settingTitle: String, settingValue: String, listener: OnItemClickListener, position: Int) {
            text.text = settingTitle
            number.text = settingValue
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
        val text: TextView = itemView.findViewById(R.id.item_text)
        val summary: TextView = itemView.findViewById(R.id.item_summary)
        fun bind(settingText: String, settingSummary: String?) {
            text.text = settingText
            if (settingSummary == null) summary.visibility = View.GONE
            else summary.text = settingSummary
        }
    }

    class EmptyRow(itemView: View): RecyclerView.ViewHolder(itemView)
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onSwitchChanged(position: Int, isChecked: Boolean)
}