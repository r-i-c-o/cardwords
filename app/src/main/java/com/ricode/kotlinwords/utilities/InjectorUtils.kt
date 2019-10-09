package com.ricode.kotlinwords.utilities

import android.content.Context
import com.ricode.kotlinwords.packs.Repository
import com.ricode.kotlinwords.viewmodels.LearnViewModelFactory

object InjectorUtils {

    fun provideLearnViewModelFactory(context: Context): LearnViewModelFactory {
        return LearnViewModelFactory(getRepository(context))
    }

    private fun getRepository(context: Context): Repository {
        return Repository.getInstance(context)
    }
}