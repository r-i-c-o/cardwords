package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ricode.kotlinwords.viewmodels.LearnViewModel
import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLearnBinding>(inflater,
            R.layout.fragment_learn, container, false)
        binding.vm = LearnViewModel()

        return binding.root
    }

}
