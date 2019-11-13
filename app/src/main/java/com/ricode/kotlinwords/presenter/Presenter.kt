package com.ricode.kotlinwords.presenter

import com.ricode.kotlinwords.packs.Word
import java.util.*

abstract class Presenter(private val mView: IView) : IPresenter {

    abstract var mWordList: ArrayList<Word>
    private var mIndex = 0

    override fun checkEmpty() {
        if (mWordList.isEmpty()) {
            mView.showAd()

        } else {
            incIndex()
            mView.setWord(mWordList[mIndex])
            mView.hideTranscription()
            mView.hideTranslation()
            mView.hideGuessingButtons()
            mView.showRevealButton()
        }
    }

    override fun onPositiveButtonClicked() {
        mWordList[mIndex].incTries()
        mWordList.remove(mWordList[mIndex])
        checkEmpty()
    }

    override fun startWords() {
        mView.setCardView()
        mView.setWord(mWordList[mIndex])
    }

    override fun onNegativeButtonClicked() {
        incIndex()
        mView.setWord(mWordList[mIndex])
        mView.hideTranscription()
        mView.hideTranslation()
        mView.hideGuessingButtons()
        mView.showRevealButton()
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
}
