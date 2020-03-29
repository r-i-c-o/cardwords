package com.ricode.cardwords.database

import android.content.Context
import androidx.room.Room
import com.ricode.cardwords.data.PackHelper

class PackClient private constructor(val context: Context) {
    var currentPack: WordFileDb = Room.databaseBuilder(context.applicationContext, WordFileDb::class.java,
        PackHelper(context).currentPackName).build()
    var dao: WordDao = currentPack.wordDao()

    fun setCurrentPack(name: String) {
        currentPack = Room.databaseBuilder(context.applicationContext, WordFileDb::class.java,
            name).build()
        dao = currentPack.wordDao()
    }

    companion object {
        private var instance: PackClient? = null

        fun getInstance(context: Context): PackClient {
            return instance ?: PackClient(context).also { instance = it }
        }
    }
}