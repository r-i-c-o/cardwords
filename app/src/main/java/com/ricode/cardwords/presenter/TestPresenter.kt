package com.ricode.cardwords.presenter

import android.content.Context
import com.ricode.cardwords.data.WordStates
import kotlinx.coroutines.launch

class TestPresenter(view: IView, context: Context): Presenter(view, context) {

    override suspend fun getList() = repository.getTestWords()

    override fun onPositiveButtonClicked() {
        presenterScope.launch {
            try {
                mWordList[mIndex].state = WordStates.DONE.ordinal
                updateAndRemove()

                endOrContinue()
            } catch (e: Exception) {
                showError(e)
            }
        }
    }

    override fun onNegativeButtonClicked() {
        presenterScope.launch {
            try {
                mWordList[mIndex].state = WordStates.REPEAT.ordinal
                updateAndRemove()

                endOrContinue()
            } catch (e: Exception) {
                showError(e)
            }
        }
    }

    private suspend fun updateAndRemove() {
        repository.updateState(mWordList[mIndex])
        mWordList.remove(mWordList[mIndex])
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