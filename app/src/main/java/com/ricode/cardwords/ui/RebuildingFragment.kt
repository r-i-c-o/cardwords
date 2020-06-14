package com.ricode.cardwords.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ricode.cardwords.R
import com.ricode.cardwords.data.PackNames
import com.ricode.cardwords.database.Repository
import com.ricode.cardwords.database.TxtToDbConverter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RebuildingFragment : Fragment() {

    lateinit var logo: ImageView
    lateinit var bg: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var infoText: TextView

    lateinit var repository: Repository
    lateinit var converter: TxtToDbConverter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_rebuilding, container, false)
        logo = v.findViewById(R.id.rebuildLogo)
        bg = v.findViewById(R.id.rebuildBg)
        progressBar = v.findViewById(R.id.rebuildProgress)
        infoText = v.findViewById(R.id.rebuildText)

        repository = Repository.getInstance(requireContext())
        converter = TxtToDbConverter(requireContext())

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        revealLogo()
    }

    private fun revealLogo() {
        logo.apply {
            visibility = View.VISIBLE
            alpha = 0f
        }
        val alphaAnimator = ObjectAnimator
            .ofFloat(logo, View.ALPHA, 0f, 1f)
        alphaAnimator.duration = 2000
        alphaAnimator.startDelay = 500
        alphaAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                moveAndShrinkLogo()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        alphaAnimator.start()
    }

    private fun moveAndShrinkLogo() {
        val endY: Float = (bg.height / 4).toFloat()
        val moveAnimator = ObjectAnimator
            .ofFloat(logo, View.TRANSLATION_Y, -endY)
        moveAnimator.duration = 1000

        val shrinkXAnimator = ObjectAnimator
            .ofFloat(logo, View.SCALE_X, 1f, 0.9f)
        shrinkXAnimator.duration = 1000

        val shrinkYAnimator = ObjectAnimator
            .ofFloat(logo, View.SCALE_Y, 1f, 0.9f)
        shrinkYAnimator.duration = 1000

        val logoSet = AnimatorSet()
        logoSet.play(moveAnimator).with(shrinkXAnimator).with(shrinkYAnimator).after(1000)
        logoSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                progressBar.visibility = View.VISIBLE
                infoText.visibility = View.VISIBLE
                rebuild()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        logoSet.start()
    }

    private fun rebuild() {
        lifecycleScope.launch {
            withContext(IO) { converter.convertFile(PackNames.LEARN) }
            withContext(IO) { converter.convertFile(PackNames.TEST) }
            withContext(IO) { converter.convertFile(PackNames.REPEAT) }
            withContext(IO) { converter.convertFile(PackNames.DONE) }
        }
    }

    private fun setText(text: String) {
        infoText.text = text
    }

    private fun onComplete() {
        findNavController().navigate(R.id.action_rebuildingFragment_to_startFragment)
    }
}