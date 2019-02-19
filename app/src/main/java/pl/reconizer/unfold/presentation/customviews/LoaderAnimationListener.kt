package pl.reconizer.unfold.presentation.customviews

import android.view.View
import android.view.animation.Animation

class LoaderAnimationListener(
        private val view: View,
        private val restartOffset: Long
) : Animation.AnimationListener {

    override fun onAnimationRepeat(animation: Animation?) {
        //view.alpha = 0f
        animation?.apply {
            startOffset = restartOffset
            //fillAfter = true
            start()
        }
    }

    override fun onAnimationEnd(animation: Animation?) {
        animation?.apply {
            reset()
            startOffset = restartOffset
            view.clearAnimation()
            view.startAnimation(animation)
        }
    }

    override fun onAnimationStart(animation: Animation?) {
        //view.alpha = 0f
    }
}