package com.ricode.kotlinwords.presenter

interface IPresenter {

    fun startWords()

    fun onPositiveButtonClicked()
    fun onNegativeButtonClicked()
    fun onRevealButtonClicked()

    fun checkEmpty()
}