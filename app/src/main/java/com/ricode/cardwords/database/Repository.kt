package com.ricode.cardwords.database

import android.content.Context
import com.ricode.cardwords.files.AppSettings

class Repository private constructor(private val context: Context){
    private val packClient = PackClient.getInstance(context)

    suspend fun getLearnWords(): List<Word> {
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
            return newList
        }
        return list
    }

    suspend fun getTestWords() = packClient.dao.getTestWords()

    suspend fun getWordByTitle(title: String) = packClient.dao.getWordByTitle(title)

    suspend fun updateState(word: Word) {
        packClient.dao.updateState(word)
    }

    companion object {
        private var instance: Repository? = null

        fun getInstance(context: Context): Repository {
            return instance ?: Repository(context).also { instance = it }
        }
    }

}