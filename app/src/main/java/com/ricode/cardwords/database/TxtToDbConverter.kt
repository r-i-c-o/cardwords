package com.ricode.cardwords.database

import android.content.Context
import android.util.Log
import com.ricode.cardwords.data.PackNames
import com.ricode.cardwords.data.WordManager
import com.ricode.cardwords.database.Repository.Companion.getInstance
import com.ricode.cardwords.utilities.UNICODE_EMPTY
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class TxtToDbConverter(ctx: Context) {
    private val wm: WordManager = WordManager(ctx)
    private val repository: Repository = getInstance(ctx)

    suspend fun convertFile(name: PackNames) {
        val file = wm.getFile(name)
        var line: String
        if (file.length() > UNICODE_EMPTY)
            try {
                BufferedReader(FileReader(file)).use { reader ->
                    while (reader.readLine().also { line = it } != null) {
                        val components = line.split("\t".toRegex()).toTypedArray()
                        val word =
                            repository.getWordByTitle(components[0])
                        when (name) {
                            PackNames.LEARN -> word.state = 1
                            PackNames.TEST -> word.state = 2
                            PackNames.REPEAT -> word.state = 3
                            PackNames.DONE -> word.state = 4
                        }
                        repository.updateState(word)
                        Log.i("txttodb", "converted in $name: ${word.id} ${word.title} ${word.transcription} ${word.translation}")
                    }
                }
            } catch (ioe: IOException) {
                Log.e("txttodb", "Failed to convert $name list: $ioe")
            }
    }
}