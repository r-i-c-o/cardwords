package com.ricode.cardwords.database

import android.content.Context
import androidx.room.Room

class PackClient private constructor(val context: Context) {
    var currentPack: WordFileDb? = null

    fun setCurrentPack(name: String) {
        currentPack = Room.databaseBuilder(
            context.applicationContext,
            WordFileDb::class.java,
            name).build()
    }

    companion object {
        private var instance: PackClient? = null

        fun getInstance(context: Context): PackClient {
            return instance ?: PackClient(context).also { instance = it }
        }
    }
}