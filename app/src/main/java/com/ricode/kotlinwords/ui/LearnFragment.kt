package com.ricode.kotlinwords.ui

import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.presenter.IPresenter
import com.ricode.kotlinwords.presenter.LearnPresenter
import kotlinx.android.synthetic.main.fragment_cardwords.*

class LearnFragment: LearnBaseFragment() {

    override fun setPresenter(): IPresenter {
        return LearnPresenter(this, requireContext())
    }

    override fun updateTextWordsLeft(number: Int) {
        words_left.text = getString(R.string.words_left, number)
    }

    override fun endSession() {
        val dialogFragment = TwoButtonDialog(getString(R.string.continue_dialog_text, 20))
        dialogFragment.setTargetFragment(this, REQUEST_CONTINUE)
        val fm = fragmentManager
        if (fm != null) dialogFragment.show(fm, "LearnFragment")
    }
}