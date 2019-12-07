package com.ricode.kotlinwords.presenter

import android.content.Context
import com.ricode.kotlinwords.data.PackNames

class TestPresenter(view: IView, context: Context): Presenter(view, context) {

    override fun getList() = repository.getTestWords()

    override fun onPositiveButtonClicked() {
        repository.appendToFile(PackNames.DONE, mWordList[mIndex])
        removeAndRewrite()

        endOrContinue()
    }

    override fun onNegativeButtonClicked() {
        repository.appendToFile(PackNames.REPEAT, mWordList[mIndex])
        removeAndRewrite()

        endOrContinue()
    }

    private fun removeAndRewrite() {
        mWordList.remove(mWordList[mIndex])
        repository.rewriteWordsInFile(PackNames.TEST, mWordList)
    }

    private fun endOrContinue() {
        if (mWordList.isEmpty()) {
            onEndSession()
        } else {
            incIndex()
            setCurrentWord()
            mView.updateTextWordsLeft(mWordList.size)
        }
    }
}