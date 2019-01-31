package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import pl.reconizer.cityadventure.R

class ScalableSpace @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var baseScreenSize: Int = ScalableTextView.DEFAULT_BASE_SCREEN_SIZE

//    private val displayMetrics: DisplayMetrics? by lazy {
//        val metrics = DisplayMetrics()
//        display?.getMetrics(metrics)
//        metrics
//    }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ScalableSpace)
            baseScreenSize = typedArray.getInt(R.styleable.ScalableSpace_baseScreenWidth, DEFAULT_BASE_SCREEN_SIZE)
            typedArray.recycle()
        }
        if (displayMetrics == null) {
            displayMetrics = DisplayMetrics()
            display?.getMetrics(displayMetrics)
            //metrics
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (displayMetrics != null && displayMetrics!!.widthPixels > 0) {
            val multiplier = displayMetrics!!.widthPixels / (displayMetrics!!.density * baseScreenSize)
            setMeasuredDimension((width * multiplier).toInt(), (height * multiplier).toInt())
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    companion object {
        private var displayMetrics: DisplayMetrics? = null

        const val DEFAULT_BASE_SCREEN_SIZE = 360
    }

}