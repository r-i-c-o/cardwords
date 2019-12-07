package com.ricode.kotlinwords.data

class Word(
    val title: String? = "title",
    val transcription: String? = "transcription",
    val translation: String? = "translation",
    var tries: Int = 2
) {
    fun incTries() {
        tries += 1
    }

    fun decTries() {
        if (tries < 0) tries = 0
        else tries -= 1
    }
}