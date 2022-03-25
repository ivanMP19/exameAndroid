package com.ivan.marin.exameandroid.animations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

class AnimacionAlfa {
    fun AnimacionAlfa(
        view: View?,
        alfaFinal: Float,
        duracion: Long,
        listener: Animator.AnimatorListener?
    ) {
        val firstalpha = (alfaFinal - 1) * -1
        val objectAnimator = ObjectAnimator.ofFloat(view, "Alpha", firstalpha, alfaFinal)
        objectAnimator.duration = duracion
        objectAnimator.addListener(listener)
        objectAnimator.start()
    }
}