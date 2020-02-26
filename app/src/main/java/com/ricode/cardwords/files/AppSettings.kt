package com.ricode.cardwords.files

import android.content.Context
import android.content.SharedPreferences
import com.ricode.cardwords.utilities.*

class AppSettings (context: Context) {

    private val settings: SharedPreferences = context.applicationContext.getSharedPreferences(SP_APP_SETTINGS, Context.MODE_PRIVATE)

    fun getNumberOfWords() = settings.getInt(SP_SETTINGS_NUMBER_WORDS, 20)

    fun setNumberOfWords(num: Int) {
        settings.edit().putInt(SP_SETTINGS_NUMBER_WORDS, num).apply()
    }

    fun getNumberOfTries() = settings.getInt(SP_SETTINGS_NUMBER_TRIES, 3)

    fun setNumberOfTries(i: Int) {
        settings.edit().putInt(SP_SETTINGS_NUMBER_TRIES, i).apply()
    }

    fun getDarkMode(): Boolean {
        return settings.getBoolean(SP_DARK_MODE, false)
    }

    fun setDarkMode(isActive: Boolean) {
        settings.edit().putBoolean(SP_DARK_MODE, isActive).apply()
    }

    fun getTextSize(): Int = settings.getInt(SP_TEXT_SIZE, 0)

    fun setTextSize(sizeCode: Int) {
        settings.edit().putInt(SP_TEXT_SIZE, sizeCode).apply()
    }
}
