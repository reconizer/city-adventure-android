package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class ScalableImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var baseScreenSize: Int = DEFAULT_BASE_SCREEN_SIZE

    init {
        adjustViewBounds = true
        scaleType = ImageView.ScaleType.FIT_XY
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val displayMetrics = DisplayMetrics()
        val display: Display? = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        display?.getMetrics(displayMetrics)
        if (displayMetrics.widthPixels > 0) {
            val multiplier = displayMetrics.widthPixels / (displayMetrics.density * baseScreenSize)
            setMeasuredDimension((width * multiplier).toInt(), (height * multiplier).toInt())
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    companion object {
        const val DEFAULT_BASE_SCREEN_SIZE = 360
    }
}