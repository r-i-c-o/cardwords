package com.ricode.kotlinwords.viewmodels

import androidx.lifecycle.ViewModel
import com.ricode.kotlinwords.packs.Repository

class LearnViewModel(private val repository: Repository): ViewModel() {

    private val wordList = repository.getLearnWords()
    private var index: Int = 0

    fun getWord() = wordList[index]

    fun incIndex() = index++

    fun isListNotEmpty() = wordList.isNotEmpty()
}