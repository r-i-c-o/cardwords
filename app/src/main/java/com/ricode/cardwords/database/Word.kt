package com.ricode.cardwords.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Word(
    val title: String,
    val transcription: String,
    val translation: String,
    var state: Int, // 0 - never used 1 - learn 2 - test 3 - repeat 4 - done
    @Ignore var tries: Int
) {
    @PrimaryKey var id: Int? = null

    fun incTries() {
        tries += 1
    }

    fun decTries() {
        if (tries < 0) tries = 0
        else tries -= 1
    }
}