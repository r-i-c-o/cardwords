package com.ricode.kotlinwords.presenter

import android.content.Context
import android.util.Log
import com.ricode.kotlinwords.packs.Repository
import com.ricode.kotlinwords.packs.Word
import com.ricode.kotlinwords.services.AdsService

abstract class Presenter(
    private val mView: IView,
    context: Context) : IPresenter {

    private val adService = AdsService(context)
    protected val repository = Repository.getInstance(context)

    var mWordList: ArrayList<Word> = ArrayList()
    private var mIndex = 0

    abstract fun getList(): ArrayList<Word>

    override fun onEndSession() {
        mView.hideGuessingButtons()
        mView.hideCard()
        if (adService.isAdLoaded()) {
            mView.showAd(adService.getAd())
        }
        else mView.showDialog()
    }

    override fun onHideAdButtonClicked() {
        mView.hideAd()
        mView.showDialog()
    }

    override fun onPositiveButtonClicked() {
        mWordList[mIndex].incTries()
        mWordList.remove(mWordList[mIndex])
        if (mWordList.isEmpty()) {
            onEndSession()
        } else {
            incIndex()
            setCurrentWord()
        }
    }

    override fun startSession() {
        mWordList = getList()
        Log.i("Presentr", mWordList[4].title)
        mView.setWordsCard()
        setCurrentWord()
        adService.loadAd()
    }

    override fun onNegativeButtonClicked() {
        incIndex()
        setCurrentWord()
    }

    override fun onRevealButtonClicked() {
        mView.hideRevealButton()
        mView.showGuessingButtons()
        mView.showTranscription()
        mView.showTranslation()
    }

    private fun incIndex() {
        mIndex = (mIndex + 1) % mWordList.size
    }

    private fun setCurrentWord() {
        mView.setWord(mWordList[mIndex])
        mView.hideTranscription()
        mView.hideTranslation()
        mView.hideGuessingButtons()
        mView.showRevealButton()
    }

    override fun onDestroy() {
        adService.destroyAd()
    }
}
