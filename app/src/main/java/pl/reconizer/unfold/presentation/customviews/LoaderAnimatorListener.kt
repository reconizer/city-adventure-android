package pl.reconizer.unfold.presentation.customviews

import android.animation.Animator

class LoaderAnimatorListener() : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
        animation?.start()
    }

    override fun onAnimationStart(animation: Animator?) {
    }

    override fun onAnimationCancel(animation: Animator?) {
    }
}