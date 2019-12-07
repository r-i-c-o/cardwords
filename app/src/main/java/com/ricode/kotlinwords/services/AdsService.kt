package com.ricode.kotlinwords.services

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.ricode.kotlinwords.utilities.AD_ID

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
                override fun onAdFailedToLoad(p0: Int) {
                    Log.i("AdsService", "ad loading failed")
                }
            }).build()
        adloader.loadAd(AdRequest.Builder().build())
    }



    fun destroyAd() = ad?.destroy()

}