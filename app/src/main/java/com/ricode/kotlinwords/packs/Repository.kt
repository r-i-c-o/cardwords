package com.ricode.kotlinwords.packs

import android.content.Context

//Singleton repository
class Repository private constructor(context: Context) {
    private val listComposer = ListComposer(context)
    private val wordManager = WordManager(context)

    fun getLearnWords(): ArrayList<Word> {
        return listComposer.wordsFromLearn
    }

    fun getTestWords(): ArrayList<Word> {
        return listComposer.wordsFromTest
    }

    fun removeWord() {
        
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(context: Context): Repository =
            instance ?: Repository(context).also { instance = it }
    }
}