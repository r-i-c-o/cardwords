package com.ricode.kotlinwords.presenter

import android.content.Context
import com.ricode.kotlinwords.data.Repository
import com.ricode.kotlinwords.data.Word
import com.ricode.kotlinwords.services.AdsService

abstract class Presenter(val mView: IView, context: Context) : IPresenter {

    private val adService = AdsService(context)
    protected val repository = Repository.getInstance(context)

    protected var mWordList: ArrayList<Word> = ArrayList()
    protected var mIndex = 0

    protected abstract fun getList(): ArrayList<Word>

    protected fun incIndex() {
        mIndex = (mIndex + 1) % mWordList.size
    }

    override fun onEndSession() {
        mView.hideGuessingButtons()
        mView.hideCard()

        if (adService.isAdLoaded) {
            mView.showAd(adService.getAd())
        }
        else mView.endSession()
    }

    override fun onHideAdButtonClicked() {
        mView.hideAd()
        mView.endSession()
    }

    override fun onRevealButtonClicked() {
        mView.hideRevealButton()
        mView.showGuessingButtons()
        mView.showTranscription()
        mView.showTranslation()
    }

    override fun startSession() {
        adService.loadAd()
        mWordList = getList()
        if (mWordList.isNotEmpty()) {
            mView.setWordsCard()
            mView.updateTextWordsLeft(mWordList.size)
            setCurrentWord()
        }
    }

    protected fun setCurrentWord() {
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