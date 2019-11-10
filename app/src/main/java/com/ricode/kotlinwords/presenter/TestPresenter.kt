package com.ricode.kotlinwords.presenter

import android.content.Context
import com.ricode.kotlinwords.packs.Repository
import com.ricode.kotlinwords.packs.Word
import java.util.*

class TestPresenter(view: IView, context: Context): Presenter(view) {
    override var mWordList: ArrayList<Word> = Repository.getInstance(context).getTestWords()
}