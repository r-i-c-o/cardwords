package com.ricode.cardwords.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ Word::class ], version = 1)
abstract class WordFileDb: RoomDatabase() {
    abstract fun wordDao(): WordDao
}