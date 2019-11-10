package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.packs.Word
import com.ricode.kotlinwords.presenter.IView
import com.ricode.kotlinwords.presenter.LearnPresenter
import kotlinx.android.synthetic.main.fragment_learn.*

class LearnFragment : Fragment(), IView{

    private lateinit var mPresenter: LearnPresenter
    private lateinit var mAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = LearnPresenter(this, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter.startWords()

        button_reveal.setOnClickListener {
            mPresenter.onRevealButtonClicked()
        }
        button_correct.setOnClickListener {
            mPresenter.onPositiveButtonClicked()
        }
        button_incorrect.setOnClickListener {
            mPresenter.onNegativeButtonClicked()
        }

        loadAd()
    }

    private fun loadAd() {
        mAd = InterstitialAd(activity)
        mAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mAd.loadAd(AdRequest.Builder().build())
    }

    override fun showDialog() {
        findNavController().navigate(R.id.action_learnFragment_to_continue_dialog_fragment2)
    }

    override fun setWord(word: Word) {
        text_word.text = word.title
        text_transcribe.text = word.transcription
        text_translate.text = word.translation
    }

    override fun showTranscription() {
        text_transcribe.visibility = View.VISIBLE
    }

    override fun showTranslation() {
        text_translate.visibility = View.VISIBLE
    }

    override fun showRevealButton() {
        button_reveal.visibility = View.VISIBLE
    }

    override fun hideRevealButton() {
        button_reveal.visibility = View.INVISIBLE
    }

    override fun hideTranscription() {
        text_transcribe.visibility = View.INVISIBLE
    }

    override fun showAd() {
        if (mAd.isLoaded) mAd.show()
    }

    override fun hideTranslation() {
        text_translate.visibility = View.INVISIBLE
    }

    override fun showGuessingButtons() {
        button_correct.visibility = View.VISIBLE
        button_incorrect.visibility = View.VISIBLE
    }

    override fun hideGuessingButtons() {
        button_correct.visibility = View.INVISIBLE
        button_incorrect.visibility = View.INVISIBLE
    }

}