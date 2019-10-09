package com.ricode.kotlinwords.packs

import android.content.Context

//Singleton repository
class Repository private constructor(val mContext: Context) {

    fun getLearnWords(): List<Word> {
        return emptyList()
    }

    fun getTestWords(): List<Word> {
        return emptyList()
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(context: Context): Repository =
            instance ?: Repository(context).also { instance = it }
    }
}