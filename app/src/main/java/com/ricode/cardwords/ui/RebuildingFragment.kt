package com.ricode.cardwords.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ricode.cardwords.R
import com.ricode.cardwords.data.PackNames
import com.ricode.cardwords.database.Repository
import com.ricode.cardwords.database.TxtToDbConverter
import com.ricode.cardwords.files.AppSettings
import com.ricode.cardwords.files.AssetHelper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RebuildingFragment : Fragment() {

    lateinit var logo: ImageView
    lateinit var bg: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var infoText: TextView
    lateinit var repeatButton: ImageView

    lateinit var repository: Repository
    lateinit var converter: TxtToDbConverter
    lateinit var assetHelper: AssetHelper

    override fun onDestroy() {
        super.onDestroy()
        Log.i("rebuiltfrag", "onDestroy: operation canceled")
        lifecycleScope.cancel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_rebuilding, container, false)
        logo = v.findViewById(R.id.rebuildLogo)
        bg = v.findViewById(R.id.rebuildBg)
        progressBar = v.findViewById(R.id.rebuildProgress)
        infoText = v.findViewById(R.id.rebuildText)
        repeatButton = v.findViewById(R.id.repeat_button)

        repository = Repository.getInstance(requireContext())
        converter = TxtToDbConverter(requireContext())
        assetHelper = AssetHelper(requireContext())

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        revealLogo()
        repeatButton.setOnClickListener {
            rebuild()
        }
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
        Log.i("rebuiltfrag", "rebuilding")
        repeatButton.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        infoText.visibility = View.VISIBLE
        lifecycleScope.launch {
            val db = context?.getDatabasePath("en_ru_common.db")?.absoluteFile
            db?.parentFile?.deleteRecursively()

            setText("Импорт наборов слов")
            withContext(IO) { assetHelper.loadFiles() }

            var learnCheck = false
            var testCheck = false
            var repCheck = false
            var doneCheck = false
            setText("Импорт списка слов для запоминания")
            withContext(IO) { learnCheck = converter.convertFile(PackNames.LEARN) }
            setText("Импорт списка слов для проверки")
            withContext(IO) { testCheck = converter.convertFile(PackNames.TEST) }
            setText("Импорт списка слов для повтора")
            withContext(IO) { repCheck = converter.convertFile(PackNames.REPEAT) }
            setText("Импорт списка выученных слов")
            withContext(IO) { doneCheck = converter.convertFile(PackNames.DONE) }
            if (learnCheck && testCheck && repCheck && doneCheck) {
                setText("Импорт успешно завершён")
                delay(1000)
                onComplete()
            }
            else
                onFail()
        }
    }

    private fun setText(text: String) {
        infoText.text = text
    }

    private fun onComplete() {
        //TODO delete txt files
        AppSettings(requireContext()).setRebuilt(true)
        findNavController().navigate(R.id.action_rebuildingFragment_to_startFragment)
    }

    private fun onFail(text: String? = null) {
        progressBar.visibility = View.INVISIBLE
        if (text == null) setText("Импорт данных завершился с ошибкой.")
        else setText(text)
        repeatButton.visibility = View.VISIBLE
    }
}