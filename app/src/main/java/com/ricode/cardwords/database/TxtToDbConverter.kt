package com.ricode.cardwords.database

import android.content.Context
import android.util.Log
import com.ricode.cardwords.data.PackNames
import com.ricode.cardwords.data.WordManager
import com.ricode.cardwords.data.WordStates
import com.ricode.cardwords.database.Repository.Companion.getInstance
import com.ricode.cardwords.utilities.UNICODE_EMPTY

class TxtToDbConverter(ctx: Context) {
    private val wm: WordManager = WordManager(ctx)
    private val repository: Repository = getInstance(ctx)

    suspend fun convertFile(name: PackNames): Boolean {
        val file = wm.getFile(name)
        var wordList: List<String> = ArrayList()
        if (file.length() > UNICODE_EMPTY)
            try {
                file.useLines {
                    wordList = it.toList()
                }
                if (wordList.isEmpty()) return false
                else
                    wordList.forEach {
                        val components = it.split("\t".toRegex()).toTypedArray()
                        val word =
                            repository.getWordByTitle(components[0])
                        when (name) {
                            PackNames.LEARN -> word.state = WordStates.LEARN.ordinal
                            PackNames.TEST -> word.state = WordStates.TEST.ordinal
                            PackNames.REPEAT -> word.state = WordStates.REPEAT.ordinal
                            PackNames.DONE -> word.state = WordStates.DONE.ordinal
                        }
                        repository.updateState(word)
                        Log.i("txttodb", "converted in ${name.name}: ${word.id} ${word.title} ${word.transcription} ${word.translation}")
                    }
                return true
            } catch (e: Exception) {
                Log.e("txttodb", "Failed to convert ${name.name} list: $e")
                return false
            }
        return true
    }
}