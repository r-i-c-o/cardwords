package com.ricode.kotlinwords.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.presenter.TestPresenter


class TestFragment : Fragment() {

    private lateinit var mPresenter: TestPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }


}
