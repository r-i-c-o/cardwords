package com.ricode.kotlinwords.presenter

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.ricode.kotlinwords.packs.Word

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

    fun setWordsCard()
    fun hideCard()

    //fun isAdLoaded(): Boolean
    fun showAd(ad: UnifiedNativeAd)
    fun hideAd()

    fun showDialog()
}