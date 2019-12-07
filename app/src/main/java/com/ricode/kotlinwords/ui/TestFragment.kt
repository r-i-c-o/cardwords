package com.ricode.kotlinwords.ui


import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.presenter.IPresenter
import com.ricode.kotlinwords.presenter.TestPresenter
import kotlinx.android.synthetic.main.fragment_learn.*

// TODO come up with endSession
class TestFragment : LearnBaseFragment() {

    override fun setPresenter(): IPresenter {
        return TestPresenter(this, requireContext())
    }

    override fun updateTextWordsLeft(number: Int) {
        words_left.text = getString(R.string.words_left, number)
    }

    override fun endSession() {

    }


}
