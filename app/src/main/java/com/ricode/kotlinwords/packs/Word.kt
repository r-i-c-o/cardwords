package com.ricode.kotlinwords.packs

class Word(
    val title: String? = "title",
    val transcription: String? = "transcription",
    val translation: String? = "translation",
    var tries: Int = 2
) {
    fun incTries() = tries + 1

    fun decTries(): Int {
        if (tries < 0) return 0
        else return tries - 1
    }
}