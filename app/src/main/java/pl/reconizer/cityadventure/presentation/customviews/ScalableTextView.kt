package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.appcompat.widget.AppCompatTextView
import pl.reconizer.cityadventure.R

class ScalableTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val displayMetrics: DisplayMetrics? by lazy {
        val metrics = DisplayMetrics()
        display?.getMetrics(metrics)
        metrics
    }

    private var baseScreenSize: Int = DEFAULT_BASE_SCREEN_SIZE

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ScalableTextView)
            baseScreenSize = typedArray.getInt(R.styleable.ScalableTextView_baseScreenWidth, DEFAULT_BASE_SCREEN_SIZE)
            typedArray.recycle()
        }

        displayMetrics?.let {
            if (it.widthPixels > 0) {
                val baseScreenSizeInPixels = it.density * baseScreenSize
                textSize = textSize * it.widthPixels / baseScreenSizeInPixels / it.density
            }
        }
    }

    companion object {
        const val DEFAULT_BASE_SCREEN_SIZE = 360
    }

}