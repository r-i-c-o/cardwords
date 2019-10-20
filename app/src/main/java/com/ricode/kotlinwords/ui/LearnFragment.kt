package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.databinding.FragmentLearnBinding
import com.ricode.kotlinwords.utilities.InjectorUtils
import com.ricode.kotlinwords.viewmodels.LearnViewModel

class LearnFragment : Fragment() {

    private val viewModel: LearnViewModel by viewModels {
        InjectorUtils.provideLearnViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLearnBinding>(inflater,
            R.layout.fragment_learn, container, false)
        binding.vm = viewModel

        return binding.root
    }

}
