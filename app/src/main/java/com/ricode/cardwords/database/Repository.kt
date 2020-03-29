package com.ricode.cardwords.database

import android.content.Context
import com.ricode.cardwords.files.AppSettings

class Repository private constructor(private val context: Context){
    private val packClient = PackClient.getInstance(context)

    fun getLearnWords(): List<Word> {
        val dao = packClient.dao
        val list = dao.getLearnWords()
        if (list.isEmpty()) {
            val settings = AppSettings(context)
            val numOfWords = settings.getNumberOfWords()
            val newList = packClient.dao.getRepeatWords()

            newList.forEach {
                it.tries = settings.getNumberOfTries() - 1
                dao.updateState(it)
            }
        }
        return list
    }

    fun getTestWords() = packClient.dao.getTestWords()

    fun getWordByTitle(title: String) = packClient.dao.getWordByTitle(title)

    fun updateState(word: Word) {
        packClient.dao.updateState(word)
    }

    public companion object {
        private var instance: Repository? = null

        fun getInstance(context: Context): Repository {
            return instance ?: Repository(context).also { instance = it }
        }
    }

}