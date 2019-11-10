package com.ricode.kotlinwords.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.ricode.kotlinwords.R
import com.ricode.kotlinwords.packs.Word
import com.ricode.kotlinwords.presenter.IView
import com.ricode.kotlinwords.presenter.LearnPresenter
import kotlinx.android.synthetic.main.card_frame.*
import kotlinx.android.synthetic.main.fragment_learn.*

class LearnFragment : Fragment(), IView{

    private lateinit var mPresenter: LearnPresenter
    private var mAd: UnifiedNativeAd? = null
    private lateinit var cardView: View

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

    }

    override fun populateNativeAd(ad: UnifiedNativeAd, view: UnifiedNativeAdView) {
        mAd?.destroy()
        mAd = ad
        // Set the media view.
        view.mediaView = view.findViewById(R.id.ad_media)

        // Set other ad assets.
        view.headlineView = view.findViewById(R.id.ad_headline)
        view.bodyView = view.findViewById(R.id.ad_body)
        view.callToActionView = view.findViewById(R.id.ad_call_to_action)
        view.iconView = view.findViewById(R.id.ad_app_icon)
        view.priceView = view.findViewById(R.id.ad_price)
        view.starRatingView = view.findViewById(R.id.ad_stars)
        view.storeView = view.findViewById(R.id.ad_store)
        view.advertiserView = view.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (view.headlineView as TextView).text = ad.headline
        view.mediaView.setMediaContent(ad.mediaContent)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
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

    private fun loadAd() {
        val adloader = AdLoader.Builder(activity, "ca-app-pub-3940256099942544/2247696110")
            .forUnifiedNativeAd {
                val adView = LayoutInflater.from(activity).inflate(R.layout.ad_unified, null) as UnifiedNativeAdView
                populateNativeAd(it, adView)
                fragment_learn_frame.removeAllViews()
                fragment_learn_frame.addView(adView)
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: Int) {
                    Log.i("LearnFr", "ad loading failed")
                }
            }).build()
        adloader.loadAd(AdRequest.Builder().build())
    }

    override fun setCardView() {
        cardView = LayoutInflater.from(activity).inflate(R.layout.card_frame, null)
        fragment_learn_frame.addView(cardView)
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
        loadAd()
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