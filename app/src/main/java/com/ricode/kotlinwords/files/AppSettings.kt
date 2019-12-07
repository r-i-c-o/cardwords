package com.ricode.kotlinwords.files

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ricode.kotlinwords.utilities.SP_APP_SETTINGS
import com.ricode.kotlinwords.utilities.SP_SETTINGS_NUMBER_TRIES
import com.ricode.kotlinwords.utilities.SP_SETTINGS_NUMBER_WORDS

//Singleton app settings class
class AppSettings (context: Context) {

    //private val mContext: Context = context.applicationContext

    init {
        Log.i("ObjCounter", "appsettings created")
    }

    private val settings: SharedPreferences = context.applicationContext.getSharedPreferences(SP_APP_SETTINGS, Context.MODE_PRIVATE)

    private fun appSettings() = settings

    fun getNumberOfWords() = appSettings().getInt(SP_SETTINGS_NUMBER_WORDS, 20)

    fun setNumberOfWords(num: Int) {
        appSettings().edit().putInt(SP_SETTINGS_NUMBER_WORDS, num).apply()
    }

    fun getNumberOfTries() = appSettings().getInt(SP_SETTINGS_NUMBER_TRIES, 3)

    fun setNumberOfTries(i: Int) {
        appSettings().edit().putInt(SP_SETTINGS_NUMBER_TRIES, i).apply()
    }

    /*companion object {
        private var instance: AppSettings? = null
        fun getInstance(context: Context): AppSettings =
            instance ?: AppSettings(context).also { instance = it }
    }*/
}
