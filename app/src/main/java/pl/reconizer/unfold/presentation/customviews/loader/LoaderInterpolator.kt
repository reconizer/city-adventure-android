package pl.reconizer.unfold.presentation.customviews.loader

import android.animation.TimeInterpolator
import android.view.animation.DecelerateInterpolator

class LoaderInterpolator(private val changeDuration: Long, pauseDuration: Long) : TimeInterpolator {

    private val changeInterpolator = DecelerateInterpolator()

    private val fullTime = 2 * changeDuration + pauseDuration

    override fun getInterpolation(input: Float): Float {
        val currentTimeValue = input * fullTime
        return if (input * fullTime < changeDuration) {
            changeInterpolator.getInterpolation(currentTimeValue/ changeDuration)
        } else if (input * fullTime < fullTime - changeDuration) {
            1f
        } else {
            changeInterpolator.getInterpolation(1 - ((currentTimeValue - (fullTime - changeDuration)) / changeDuration))
        }
    }

}