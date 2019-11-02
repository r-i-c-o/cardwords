package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.packs.Repository
import com.ricode.kotlinwords.packs.Word
import com.ricode.kotlinwords.presenter.IView
import kotlinx.android.synthetic.main.fragment_learn.*

class LearnFragment : Fragment(), IView{

    private lateinit var wordList: ArrayList<Word>
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordList = Repository.getInstance(requireContext()).getLearnWords() as ArrayList<Word>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (wordList.isEmpty())
            noSetup()
        else setWord()

        button_reveal.setOnClickListener {
            showTranscription()
            showTranslation()
            hideRevealButton()
            showGuessingButtons()
        }
        button_correct.setOnClickListener {
            wordList[index].incTries()
            wordList.remove(wordList[index])
            if (wordList.isEmpty()) noSetup()
            else showNextWord()
        }
        button_incorrect.setOnClickListener {
            showNextWord()
        }
    }

    private fun showNextWord() {
        incIndex()
        setWord()
        hideGuessingButtons()
        showRevealButton()
        hideTranscription()
        hideTranslation()
    }

    private fun incIndex() {
        index = (index + 1) % wordList.size
        Log.i("LearnFr", "size ${wordList.size}, index $index")
    }

    private fun noSetup() {
        hideGuessingButtons()
        hideRevealButton()
    }

    override fun setWord() {
        text_word.text = wordList[index].title
        text_transcribe.text = wordList[index].transcription
        text_translate.text = wordList[index].translation
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
