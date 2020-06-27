package com.ricode.cardwords.database

import android.content.Context
import androidx.room.Room
import com.ricode.cardwords.data.PackHelper

class PackClient private constructor(val context: Context) {
    lateinit var currentPack: WordFileDb
    lateinit var dao: WordDao

    fun setCurrentPack(name: String) {
        currentPack = Room.databaseBuilder(context.applicationContext, WordFileDb::class.java,
            name).build()
        dao = currentPack.wordDao()
    }

    fun deletePack(name: String) {

    }

    fun importDbFromAssets() {
        currentPack = Room
            .databaseBuilder(context.applicationContext, WordFileDb::class.java, "en_ru_common.db")
            .createFromAsset("words_file/en_ru_common.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = currentPack.wordDao()
    }

    companion object {
        private var instance: PackClient? = null

        fun getInstance(context: Context): PackClient {
            return instance ?: PackClient(context).also { instance = it }
        }
    }
}