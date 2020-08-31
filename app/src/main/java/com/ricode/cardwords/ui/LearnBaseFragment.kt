package com.ricode.cardwords.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.ricode.cardwords.R
import com.ricode.cardwords.database.Word
import com.ricode.cardwords.presenter.IPresenter
import com.ricode.cardwords.presenter.IView
import kotlinx.android.synthetic.main.fragment_cardwords.*

const val REQUEST_CONTINUE = 1
private const val TAG = "LearnBaseFragment"
abstract class LearnBaseFragment : Fragment(), IView{

    private lateinit var mPresenter: IPresenter

    private lateinit var textWord: TextView
    private lateinit var textTranscription: TextView
    private lateinit var textTranslation: TextView
    private lateinit var cardFrame: FrameLayout

    protected abstract fun setPresenter(): IPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = setPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_cardwords, container, false)
        cardFrame = v.findViewById(R.id.fragment_learn_frame)
        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter.startSession()

        button_neutral.setOnClickListener {
            mPresenter.onNeutralButtonClicked()
        }
        button_positive.setOnClickListener {
            mPresenter.onPositiveButtonClicked()
        }
        button_negative.setOnClickListener {
            mPresenter.onNegativeButtonClicked()
        }
        button_speak.setOnClickListener {
            mPresenter.onSpeakButtonClicked()
        }
        button_back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CONTINUE) {
            when (resultCode) {
                Activity.RESULT_OK -> mPresenter.startSession()
                Activity.RESULT_CANCELED -> findNavController().navigateUp()
            }
        }
    }

    private fun populateNativeAd(ad: UnifiedNativeAd, view: UnifiedNativeAdView) {
        view.mediaView = view.findViewById(R.id.ad_media)

        view.headlineView = view.findViewById(R.id.ad_headline)
        view.bodyView = view.findViewById(R.id.ad_body)
        view.callToActionView = view.findViewById(R.id.ad_call_to_action)
        view.iconView = view.findViewById(R.id.ad_app_icon)
        view.priceView = view.findViewById(R.id.ad_price)
        view.starRatingView = view.findViewById(R.id.ad_stars)
        view.storeView = view.findViewById(R.id.ad_store)
        view.advertiserView = view.findViewById(R.id.ad_advertiser)

        (view.headlineView as TextView).text = ad.headline
        view.mediaView.setMediaContent(ad.mediaContent)

        if (ad.body == null) {
            view.bodyView.visibility = View.INVISIBLE
        } else {
            view.bodyView.visibility = View.VISIBLE
            (view.bodyView as TextView).text = ad.body
        }

        if (ad.callToAction == null) {
            view.callToActionView.visibility = View.INVISIBLE
        } else {
            view.callToActionView.visibility = View.VISIBLE
            (view.callToActionView as Button).text = ad.callToAction
        }

        if (ad.icon == null) {
            view.iconView.visibility = View.GONE
        } else {
            (view.iconView as ImageView).setImageDrawable(
                ad.icon.drawable)
            view.iconView.visibility = View.VISIBLE
        }

        if (ad.price == null) {
            view.priceView.visibility = View.INVISIBLE
        } else {
            view.priceView.visibility = View.VISIBLE
            (view.priceView as TextView).text = ad.price
        }

        if (ad.store == null) {
            view.storeView.visibility = View.INVISIBLE
        } else {
            view.storeView.visibility = View.VISIBLE
            (view.storeView as TextView).text = ad.store
        }

        if (ad.starRating == null) {
            view.starRatingView.visibility = View.INVISIBLE
        } else {
            (view.starRatingView as RatingBar).rating = ad.starRating!!.toFloat()
            view.starRatingView.visibility = View.VISIBLE
        }

        if (ad.advertiser == null) {
            view.advertiserView.visibility = View.INVISIBLE
        } else {
            (view.advertiserView as TextView).text = ad.advertiser
            view.advertiserView.visibility = View.VISIBLE
        }
        view.setNativeAd(ad)
    }

    override fun setWordsCard(size: Int) {
        val wordView = LayoutInflater.from(activity).inflate(R.layout.card_frame, cardFrame, false)
        textWord = wordView.findViewById(R.id.text_word)
        textWord.textSize = (36 + size * 10).toFloat()
        textTranscription = wordView.findViewById(R.id.text_transcribe)
        textTranscription.textSize = (18 + size * 8).toFloat()
        textTranslation = wordView.findViewById(R.id.text_translate)
        textTranslation.textSize = (18 + size * 8).toFloat()
        cardFrame.addView(wordView)
        words_left.visibility = View.VISIBLE
        button_neutral.setText(R.string.button_show)
    }

    override fun hideCard() {
        cardFrame.removeAllViews()
        words_left.visibility = View.GONE
    }

    override fun setWord(word: Word) {
        textWord.text = word.title
        textTranscription.text = word.transcription
        textTranslation.text = word.translation
    }

    override fun showTranscription() {
        textTranscription.visibility = View.VISIBLE
    }

    override fun showTranslation() {
        textTranslation.visibility = View.VISIBLE
    }

    override fun showRevealButton() {
        button_neutral.visibility = View.VISIBLE
    }

    override fun hideRevealButton() {
        button_neutral.visibility = View.INVISIBLE
    }

    override fun hideTranscription() {
        textTranscription.visibility = View.INVISIBLE
    }

    override fun showAd(ad: UnifiedNativeAd) {
        ad_card_text.visibility = View.VISIBLE
        button_neutral.visibility = View.VISIBLE
        button_neutral.setText(R.string.button_skip_ad)
        val adView = LayoutInflater.from(activity).inflate(R.layout.ad_frame, cardFrame, false) as UnifiedNativeAdView
        populateNativeAd(ad, adView)
        cardFrame.addView(adView)
    }

    override fun hideAd() {
        ad_card_text.visibility = View.GONE
        button_neutral.visibility = View.GONE
        cardFrame.removeAllViews()
    }

    override fun hideTranslation() {
        textTranslation.visibility = View.INVISIBLE
    }

    override fun showGuessingButtons() {
        button_positive.visibility = View.VISIBLE
        button_negative.visibility = View.VISIBLE
    }

    override fun hideGuessingButtons() {
        button_positive.visibility = View.INVISIBLE
        button_negative.visibility = View.INVISIBLE
    }

    override fun showError(e: Exception) {
        hideGuessingButtons()
        hideRevealButton()
        hideCard()
        error_text.visibility = View.VISIBLE
        Log.e(TAG, "showError: ${e.printStackTrace()}")
    }
}