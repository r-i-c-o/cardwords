package com.ricode.kotlinwords.viewmodels

import androidx.lifecycle.ViewModel
import com.ricode.kotlinwords.packs.Word

class LearnViewModel(private val wordList: List<Word>): ViewModel() {

    private var index: Int = 0
    private var showWord: Boolean = false

    fun getWord() = wordList[index]

    fun getListSize() = wordList.size

    fun incIndex() {
        index = (index + 1) % wordList.size
    }

    fun isListNotEmpty() = wordList.isNotEmpty()
}