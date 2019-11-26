package com.ricode.kotlinwords.presenter

import android.content.Context
import com.ricode.kotlinwords.packs.Word
import java.util.*

class TestPresenter(view: IView, context: Context): Presenter(view, context) {
    override fun getList(): ArrayList<Word> = repository.getTestWords()
}