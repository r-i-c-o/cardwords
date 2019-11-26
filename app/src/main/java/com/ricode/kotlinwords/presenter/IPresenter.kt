package com.ricode.kotlinwords.presenter

interface IPresenter {

    fun startSession()

    fun onPositiveButtonClicked()
    fun onNegativeButtonClicked()
    fun onRevealButtonClicked()

    fun onHideAdButtonClicked()

    fun onEndSession()

    fun onDestroy()
}