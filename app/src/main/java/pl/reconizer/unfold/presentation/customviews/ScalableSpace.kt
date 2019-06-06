package pl.reconizer.unfold.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.getDisplayMetrics

class ScalableSpace @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var baseScreenWidth: Int = DEFAULT_WIDTH_SCREEN_SIZE
    private var baseScreenHeight: Int = DEFAULT_HEIGHT_SCREEN_SIZE
    private var scaleDirection: Int = WIDTH_SCALE_DIRECTION_TYPE

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ScalableSpace)
            baseScreenWidth = typedArray.getInt(R.styleable.ScalableSpace_baseScreenWidth, DEFAULT_WIDTH_SCREEN_SIZE)
            baseScreenHeight = typedArray.getInt(R.styleable.ScalableSpace_baseScreenHeight, DEFAULT_HEIGHT_SCREEN_SIZE)
            scaleDirection = typedArray.getInt(R.styleable.ScalableSpace_scaleDirection, WIDTH_SCALE_DIRECTION_TYPE)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (getDisplayMetrics().widthPixels > 0 && getDisplayMetrics().heightPixels > 0) {
            setMeasuredDimension((width * calculateWidthMultiplier()).toInt(), (height * calculateHeightMultiplier()).toInt())
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun calculateWidthMultiplier(): Float {
        return if (scaleDirection and WIDTH_SCALE_DIRECTION_TYPE == WIDTH_SCALE_DIRECTION_TYPE) {
            getDisplayMetrics().widthPixels / (getDisplayMetrics().density * baseScreenWidth)
        } else {
            1f
        }
    }

    private fun calculateHeightMultiplier(): Float {
        return if (scaleDirection and HEIGHT_SCALE_DIRECTION_TYPE == HEIGHT_SCALE_DIRECTION_TYPE) {
            getDisplayMetrics().heightPixels / (getDisplayMetrics().density * baseScreenHeight)
        } else {
            1f
        }
    }

    companion object {
        const val DEFAULT_WIDTH_SCREEN_SIZE = 360
        const val DEFAULT_HEIGHT_SCREEN_SIZE = 640

        const val HEIGHT_SCALE_DIRECTION_TYPE = 1
        const val WIDTH_SCALE_DIRECTION_TYPE = 2
    }

}