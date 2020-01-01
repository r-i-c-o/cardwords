package com.ricode.cardwords.ui

import com.ricode.cardwords.R
import com.ricode.cardwords.presenter.IPresenter
import com.ricode.cardwords.presenter.LearnPresenter
import kotlinx.android.synthetic.main.fragment_cardwords.*

class LearnFragment: LearnBaseFragment() {

    override fun setPresenter(): IPresenter {
        return LearnPresenter(this, requireContext())
    }

    override fun updateTextWordsLeft(number: Int) {
        words_left.text = getString(R.string.words_left_learn, number)
    }

    override fun endSession() {
        val dialogFragment = TwoButtonDialog(getString(R.string.continue_dialog_text))
        dialogFragment.setTargetFragment(this, REQUEST_CONTINUE)
        val fm = fragmentManager
        if (fm != null) dialogFragment.show(fm, "LearnFragment")
    }
}