package com.ricode.cardwords.presenter

interface IPresenter {

    fun startSession()

    fun onPositiveButtonClicked()
    fun onNegativeButtonClicked()
    fun onSpeakButtonClicked()

    fun onNeutralButtonClicked()
    fun onRevealButtonClicked()
    fun onHideAdButtonClicked()

    fun onEndSession()

    fun onDestroy()
}