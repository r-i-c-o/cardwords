package com.ricode.cardwords.database

import android.content.Context
import androidx.room.Room

class PackClient private constructor(val context: Context) {
    var currentPack: WordFileDb? = null
    var dao: WordDao? = null

    fun setCurrentPack(name: String) {
        if (name.isNotEmpty()) {
            currentPack = Room.databaseBuilder(
                context.applicationContext,
                WordFileDb::class.java,
                name).build()
            dao = currentPack?.wordDao()
        }
    }

    companion object {
        private var instance: PackClient? = null

        fun getInstance(context: Context): PackClient {
            return instance ?: PackClient(context).also { instance = it }
        }
    }
}