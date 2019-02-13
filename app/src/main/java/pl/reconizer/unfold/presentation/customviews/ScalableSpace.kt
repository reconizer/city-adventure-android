package pl.reconizer.unfold.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.getDisplayMetrics

class ScalableSpace @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var baseScreenSize: Int = ScalableTextView.DEFAULT_BASE_SCREEN_SIZE

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ScalableSpace)
            baseScreenSize = typedArray.getInt(R.styleable.ScalableSpace_baseScreenWidth, DEFAULT_BASE_SCREEN_SIZE)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (getDisplayMetrics().widthPixels > 0) {
            val multiplier = getDisplayMetrics().widthPixels / (getDisplayMetrics().density * baseScreenSize)
            setMeasuredDimension((width * multiplier).toInt(), (height * multiplier).toInt())
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    companion object {
        const val DEFAULT_BASE_SCREEN_SIZE = 360
    }

}