package com.ricode.kotlinwords.files

import android.content.Context
import com.ricode.kotlinwords.utilities.SP_APP_SETTINGS
import com.ricode.kotlinwords.utilities.SP_SETTINGS_NUMBER_WORDS

class Settings(context: Context) {

    private val mContext: Context = context.applicationContext

    val numberOfWords: Int
        get() = mContext.getSharedPreferences(SP_APP_SETTINGS, Context.MODE_PRIVATE)
            .getInt(SP_SETTINGS_NUMBER_WORDS, 20)

    fun setNumberOfWords(num: Int) {
        mContext.getSharedPreferences(SP_APP_SETTINGS, Context.MODE_PRIVATE)
            .edit()
            .putInt(SP_SETTINGS_NUMBER_WORDS, 20)
            .apply()
    }
}
