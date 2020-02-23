package com.ricode.cardwords.services

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class TtsService(context: Context): TextToSpeech.OnInitListener {

    private val ttsService = TextToSpeech(context, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            ttsService.language = Locale.UK
            ttsService.setSpeechRate(0.5f)
        }
    }

    fun speak(word: String) {
        ttsService.speak(word, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun shutDown() {
        ttsService.shutdown()
    }


}