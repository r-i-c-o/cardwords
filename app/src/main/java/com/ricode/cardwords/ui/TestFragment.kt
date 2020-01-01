package com.ricode.cardwords.ui


import androidx.navigation.fragment.findNavController
import com.ricode.cardwords.R
import com.ricode.cardwords.presenter.IPresenter
import com.ricode.cardwords.presenter.TestPresenter
import kotlinx.android.synthetic.main.fragment_cardwords.*

class TestFragment : LearnBaseFragment() {

    override fun setPresenter(): IPresenter {
        return TestPresenter(this, requireContext())
    }

    override fun updateTextWordsLeft(number: Int) {
        words_left.text = getString(R.string.words_left_test, number)
    }

    override fun endSession() {
        findNavController().navigateUp()
    }


}
