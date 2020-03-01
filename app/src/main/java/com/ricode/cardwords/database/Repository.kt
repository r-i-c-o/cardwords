package com.ricode.cardwords.database

import android.content.Context
import com.ricode.cardwords.files.AppSettings

class Repository private constructor(private val context: Context){
    private val packClient = PackClient.getInstance(context)

    fun getLearnWords(): List<Word>? {
        val list = packClient.currentPack?.wordDao()?.getLearnWords()
        if (list!!.isEmpty()) {
            val numOfWords = AppSettings(context).getNumberOfWords()

        }
        return list
    }

    fun getTestWords() = packClient.currentPack?.wordDao()?.getTestWords()

    companion object {
        private var instance: Repository? = null

        fun getInstance(context: Context): Repository {
            return instance ?: Repository(context).also { instance = it }
        }
    }

}