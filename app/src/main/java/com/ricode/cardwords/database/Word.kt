package com.ricode.cardwords.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Word(
    var title: String = "title",
    var transcription: String = "transcription",
    var translation: String = "translation",
    var state: Int = 0 // 0 - never used 1 - learn 2 - test 3 - repeat 4 - done
) {
    @PrimaryKey var id: Int = 0
    @Ignore var tries: Int = 2

    fun incTries() {
        tries += 1
    }

    fun decTries() {
        if (tries < 0) tries = 0
        else tries -= 1
    }
}