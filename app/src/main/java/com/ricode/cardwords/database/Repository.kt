package com.ricode.cardwords.database

import android.content.Context
import com.ricode.cardwords.files.AppSettings
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class Repository private constructor(private val context: Context){
    private val packClient = PackClient.getInstance(context)

    suspend fun getLearnWords(): ArrayList<Word> = withContext(IO) {
        val dao = packClient.dao
        val list: ArrayList<Word> = ArrayList(dao.getLearnWords())
        if (list.isEmpty()) {
            val settings = AppSettings(context)
            val numOfWords = settings.getNumberOfWords()
            val newList: ArrayList<Word> = ArrayList()

            val repeatList = dao.getRepeatWords()
            if (repeatList.size <= numOfWords) {
                newList.addAll(repeatList)
                newList.addAll(dao.getNUnusedWords(numOfWords - repeatList.size))
            } else {
                newList.addAll(repeatList.subList(0, 20))
            }
            newList.forEach {
                it.tries = settings.getNumberOfTries() - 1
                dao.updateState(it)
            }
            return@withContext newList
        }
        list
    }

    suspend fun getTestWords() = withContext(IO){ ArrayList(packClient.dao.getTestWords()) }

    suspend fun getWordByTitle(title: String) = withContext(IO) { packClient.dao.getWordByTitle(title) }

    suspend fun updateState(word: Word) {
        withContext(IO) { packClient.dao.updateState(word) }
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(context: Context): Repository {
            return instance ?: synchronized(this) {
                Repository(context).also { instance = it }
            }
        }
    }

}