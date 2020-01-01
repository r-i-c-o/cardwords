package com.ricode.cardwords.services

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.ricode.cardwords.utilities.AD_ID

class AdsService(private val mContext: Context) {
    private var ad: UnifiedNativeAd? = null
    var isAdLoaded = false

    fun getAd() = ad!!

    fun loadAd() {
        isAdLoaded = false
        destroyAd()

        val adloader = AdLoader.Builder(mContext, AD_ID)
            .forUnifiedNativeAd {
                ad = it
            }.withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    isAdLoaded = true
                }
            }).build()
        adloader.loadAd(AdRequest.Builder().build())
    }

    fun destroyAd() = ad?.destroy()

}