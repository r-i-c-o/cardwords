package com.ricode.kotlinwords.presenter

import android.content.Context
import com.ricode.kotlinwords.files.AppSettings
import com.ricode.kotlinwords.data.PackNames
import com.ricode.kotlinwords.data.Word

class LearnPresenter(view: IView, context: Context): Presenter(view, context) {
    private val numOfTries = AppSettings(context).getNumberOfTries()
    override fun getList(): ArrayList<Word> = repository.getLearnWords()

    override fun onPositiveButtonClicked() {
        mWordList[mIndex].incTries()
        if (mWordList[mIndex].tries == numOfTries) {
            repository.appendToFile(PackNames.TEST, mWordList[mIndex])
            mWordList.remove(mWordList[mIndex])
            repository.rewriteWordsInFile(PackNames.LEARN, mWordList)
        }
        if (mWordList.isEmpty()) {
            onEndSession()
        } else {
            showNextWord()
            mView.updateTextWordsLeft(mWordList.size)
        }
    }

    override fun onNegativeButtonClicked() {
        mWordList[mIndex].decTries()
        showNextWord()
    }

    private fun showNextWord() {
        incIndex()
        setCurrentWord()
    }
}