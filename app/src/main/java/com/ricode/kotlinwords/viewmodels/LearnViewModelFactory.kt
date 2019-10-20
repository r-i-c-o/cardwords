package com.ricode.kotlinwords.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ricode.kotlinwords.packs.Word

class LearnViewModelFactory(val list: List<Word>): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = LearnViewModel(list) as T
}