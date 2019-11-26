package com.ricode.kotlinwords.presenter

import android.content.Context
import com.ricode.kotlinwords.packs.Word

class LearnPresenter(view: IView, context: Context): Presenter(view, context) {
    override fun getList(): ArrayList<Word> = repository.getLearnWords()
}