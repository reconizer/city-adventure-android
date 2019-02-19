package pl.reconizer.unfold.presentation.customviews.loader

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_loader.view.*
import pl.reconizer.unfold.R

class Loader @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loader, this, true)

        val leftFeet = listOf(
                loaderFootLeft1,
                loaderFootLeft2,
                loaderFootLeft3,
                loaderFootLeft4,
                loaderFootLeft5,
                loaderFootLeft6,
                loaderFootLeft7,
                loaderFootLeft8,
                loaderFootLeft9,
                loaderFootLeft10,
                loaderFootLeft11,
                loaderFootLeft12
        )
        val rightFeet = listOf(
                loaderFootRight1,
                loaderFootRight2,
                loaderFootRight3,
                loaderFootRight4,
                loaderFootRight5,
                loaderFootRight6,
                loaderFootRight7,
                loaderFootRight8,
                loaderFootRight9,
                loaderFootRight10,
                loaderFootRight11,
                loaderFootRight12
        )
        animateFeet(leftFeet)
        animateFeet(rightFeet, RIGHT_FOOT_DELAY)
    }

    fun animateFeet(collection: List<ImageView>, delay: Long = 0) {
        val loaderInterpolator = LoaderInterpolator(CHANGE_ANIM_DURATION, LIFESPAN_DURATION)
        collection.forEachIndexed { index, foot ->
            val animation = ValueAnimator.ofFloat(0f, ((NUMBER_OF_ELEMENTS + 1) * CHANGE_ANIM_DURATION + LIFESPAN_DURATION).toFloat()).apply {
                startDelay = index * CHANGE_ANIM_DURATION + delay
                duration = NUMBER_OF_ELEMENTS * CHANGE_ANIM_DURATION
                interpolator = LinearInterpolator()
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.RESTART
                addUpdateListener {
                    val timeValue = it.animatedValue as Float
                    val endTime = 2 * CHANGE_ANIM_DURATION + LIFESPAN_DURATION
                    foot.alpha = loaderInterpolator.getInterpolation(timeValue / endTime)
                }
            }
            animation.start()
        }
    }

    companion object {
        const val CHANGE_ANIM_DURATION = 300L
        const val NUMBER_OF_ELEMENTS = 12
        const val LIFESPAN_DURATION = 8 * CHANGE_ANIM_DURATION
        const val ANIMATION_DURATION = (NUMBER_OF_ELEMENTS) * CHANGE_ANIM_DURATION
        const val REPEAT_COUNT = 120
        const val RIGHT_FOOT_DELAY = CHANGE_ANIM_DURATION / 2
    }
}