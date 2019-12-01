package com.ricode.kotlinwords.presenter

import android.content.Context
import com.ricode.kotlinwords.files.AppSettings
import com.ricode.kotlinwords.packs.PackNames
import com.ricode.kotlinwords.packs.Repository
import com.ricode.kotlinwords.packs.Word
import com.ricode.kotlinwords.packs.WordManager
import com.ricode.kotlinwords.services.AdsService

abstract class Presenter(private val mView: IView, context: Context) : IPresenter {

    private val adService = AdsService(context)
    protected val repository = Repository.getInstance(context)
    protected val wordManager = WordManager(context)
    private val appSettings = AppSettings(context)

    var mWordList: ArrayList<Word> = ArrayList()
    private var mIndex = 0

    abstract fun getList(): ArrayList<Word>
    abstract fun appendWordToFile(word: Word)

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
        if (mWordList[mIndex].tries == appSettings.getNumberOfTries()) {
            wordManager.appendWordToFile(PackNames.TEST, mWordList[mIndex])
            mWordList.remove(mWordList[mIndex])
            wordManager.rewriteWordsInFile(PackNames.LEARN, mWordList)
        }
        if (mWordList.isEmpty()) {
            onEndSession()
        } else {
            incIndex()
            setCurrentWord()
            mView.updateTextWordsLeft(mWordList.size)
        }
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