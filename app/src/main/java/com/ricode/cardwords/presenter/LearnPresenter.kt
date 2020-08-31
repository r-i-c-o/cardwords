package com.ricode.cardwords.presenter

import android.content.Context
import com.ricode.cardwords.data.WordStates
import com.ricode.cardwords.database.Word
import kotlinx.coroutines.launch

class LearnPresenter(view: IView, context: Context): Presenter(view, context) {
    private val numOfTries = settings.getNumberOfTries()
    override suspend fun getList(): ArrayList<Word> = repository.getLearnWords()

    override fun onPositiveButtonClicked() {
        presenterScope.launch {
            try {
                mWordList[mIndex].incTries()
                if (mWordList[mIndex].tries >= numOfTries) {
                    mWordList[mIndex].state = WordStates.TEST.ordinal
                    repository.updateState(mWordList[mIndex])
                    mWordList.removeAt(mIndex)
                }
                if (mWordList.isEmpty()) {
                    onEndSession()
                } else {
                    showNextWord()
                    mView.updateTextWordsLeft(mWordList.size)
                }
            } catch (e: Exception) {
                showError(e)
            }
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