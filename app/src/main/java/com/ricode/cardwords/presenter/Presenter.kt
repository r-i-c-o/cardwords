package com.ricode.cardwords.presenter

import android.content.Context
import com.ricode.cardwords.data.Repository
import com.ricode.cardwords.data.Word
import com.ricode.cardwords.files.AppSettings
import com.ricode.cardwords.services.AdsService
import com.ricode.cardwords.services.TtsService

abstract class Presenter(val mView: IView, context: Context) : IPresenter {

    val settings = AppSettings(context)
    private val adService = AdsService(context)
    private val ttsService = TtsService(context)
    protected val repository = Repository.getInstance(context)
    private var isAdShown = false

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
            isAdShown = true
            mView.showAd(adService.getAd())
        }
        else mView.endSession()
    }

    override fun onNeutralButtonClicked() {
        if (!isAdShown) onRevealButtonClicked()
        else onHideAdButtonClicked()
    }

    override fun onSpeakButtonClicked() {
        ttsService.speak(mWordList[mIndex].title ?: "")
    }

    override fun onHideAdButtonClicked() {
        mView.hideAd()
        isAdShown = false
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
            mView.setWordsCard(settings.getTextSize())
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
        ttsService.shutDown()
    }
}