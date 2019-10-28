package com.ricode.kotlinwords.packs

import android.content.Context

//Singleton repository
class Repository private constructor(context: Context) {
    private val listComposer = ListComposer(context)
    private val wordManager = WordManager(context)

    fun getLearnWords(): List<Word> {
        return listComposer.wordsFromLearn
    }

    fun getTestWords(): List<Word> {
        return listComposer.wordsFromTest
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(context: Context): Repository =
            instance ?: Repository(context).also { instance = it }
    }
}