package com.ricode.kotlinwords.presenter

import android.content.Context
import com.ricode.kotlinwords.packs.Word
import java.util.*

open abstract class Presenter(private val mView: IView, context: Context) : IPresenter {

    abstract var mWordList: ArrayList<Word>
    private var mIndex = 0

    override fun onPositiveButtonClicked() {
        mWordList[mIndex].incTries()
        mWordList.remove(mWordList[mIndex])
        incIndex()
        mView.setWord(mWordList[mIndex])
        mView.hideTranscription()
        mView.hideTranslation()
        mView.hideGuessingButtons()
        mView.showRevealButton()
    }

    override fun onStart() {
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
