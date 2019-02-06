package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.common.extensions.getDisplayMetrics

class ScalableTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var baseScreenSize: Int = DEFAULT_BASE_SCREEN_SIZE

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ScalableTextView)
            baseScreenSize = typedArray.getInt(R.styleable.ScalableTextView_baseScreenWidth, DEFAULT_BASE_SCREEN_SIZE)
            typedArray.recycle()
        }
        if (getDisplayMetrics().widthPixels > 0) {
            val baseScreenSizeInPixels = getDisplayMetrics().density * baseScreenSize
            textSize = textSize * getDisplayMetrics().widthPixels / baseScreenSizeInPixels / getDisplayMetrics().density
        }
    }

    companion object {
        const val DEFAULT_BASE_SCREEN_SIZE = 360
    }

}