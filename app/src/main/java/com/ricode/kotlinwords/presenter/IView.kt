package com.ricode.kotlinwords.presenter

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
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

    fun setCardView()
    fun populateNativeAd(ad: UnifiedNativeAd, view: UnifiedNativeAdView)

    fun showAd()
    fun showDialog()
}