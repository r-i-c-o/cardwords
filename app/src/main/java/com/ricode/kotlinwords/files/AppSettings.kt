package com.ricode.kotlinwords.files

import android.content.Context
import com.ricode.kotlinwords.utilities.SP_APP_SETTINGS
import com.ricode.kotlinwords.utilities.SP_SETTINGS_NUMBER_TRIES
import com.ricode.kotlinwords.utilities.SP_SETTINGS_NUMBER_WORDS

class AppSettings(context: Context) {

    private val mContext: Context = context.applicationContext

    private fun appSettings() = mContext.getSharedPreferences(SP_APP_SETTINGS, Context.MODE_PRIVATE)

    fun getNumberOfWords() = appSettings().getInt(SP_SETTINGS_NUMBER_WORDS, 20)

    fun setNumberOfWords(num: Int) {
        appSettings().edit().putInt(SP_SETTINGS_NUMBER_WORDS, num).apply()
    }

    fun getNumberOfTries() = appSettings().getInt(SP_SETTINGS_NUMBER_TRIES, 3)

    fun setNumberOfTries(i: Int) {
        appSettings().edit().putInt(SP_SETTINGS_NUMBER_TRIES, i).apply()
    }
}
