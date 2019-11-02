package com.ricode.kotlinwords.presenter

interface IView {
    fun setWord()

    fun showTranscription()
    fun showTranslation()

    fun hideTranscription()
    fun hideTranslation()

    fun showRevealButton()
    fun hideRevealButton()

    fun showGuessingButtons()
    fun hideGuessingButtons()
}