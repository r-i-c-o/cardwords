package com.ricode.kotlinwords

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.ricode.kotlinwords.files.AppSettings

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        updateTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)
    }

    fun updateTheme() {
        if (AppSettings(this).getDarkMode()) setTheme(R.style.AppThemeDark)
        else setTheme(R.style.AppTheme)

    }
}
