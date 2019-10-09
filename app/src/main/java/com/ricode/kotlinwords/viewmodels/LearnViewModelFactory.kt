package com.ricode.kotlinwords.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ricode.kotlinwords.packs.Repository

class LearnViewModelFactory(val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = LearnViewModel(repository) as T
}