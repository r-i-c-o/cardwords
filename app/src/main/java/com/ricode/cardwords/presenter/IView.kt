package com.ricode.cardwords.presenter

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.ricode.cardwords.data.Word

interface IView {
    fun setWord(word: Word)
    fun updateTextWordsLeft(number: Int)

    fun showTranscription()
    fun showTranslation()

    fun hideTranscription()
    fun hideTranslation()

    fun showRevealButton()
    fun hideRevealButton()

    fun showGuessingButtons()
    fun hideGuessingButtons()

    fun setWordsCard(size: Int)
    fun hideCard()

    fun showAd(ad: UnifiedNativeAd)
    fun hideAd()

    fun endSession()
}