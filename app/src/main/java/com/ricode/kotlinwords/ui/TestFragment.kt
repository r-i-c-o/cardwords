package com.ricode.kotlinwords.ui


import com.ricode.kotlinwords.presenter.IPresenter
import com.ricode.kotlinwords.presenter.TestPresenter

// TODO come up with endSession
class TestFragment : LearnBaseFragment() {

    override fun setPresenter(): IPresenter {
        return TestPresenter(this, requireContext())
    }

    override fun endSession() {

    }


}
