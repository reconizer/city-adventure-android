package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import pl.reconizer.cityadventure.R

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

        val displayMetrics = DisplayMetrics()
        val display: Display? = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        display?.getMetrics(displayMetrics)
        if (displayMetrics.widthPixels > 0) {
            val baseScreenSizeInPixels = displayMetrics.density * baseScreenSize
            textSize = textSize * displayMetrics.widthPixels / baseScreenSizeInPixels / displayMetrics.density
        }
    }

    companion object {
        const val DEFAULT_BASE_SCREEN_SIZE = 360
    }

}