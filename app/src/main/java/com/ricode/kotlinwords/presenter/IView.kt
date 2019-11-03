package com.ricode.kotlinwords.presenter

import com.ricode.kotlinwords.packs.Word

interface IView {
    fun setWord(word: Word)

    fun showTranscription()
    fun showTranslation()

    fun hideTranscription()
    fun hideTranslation()

    fun showRevealButton()
    fun hideRevealButton()

    fun showGuessingButtons()
    fun hideGuessingButtons()
}