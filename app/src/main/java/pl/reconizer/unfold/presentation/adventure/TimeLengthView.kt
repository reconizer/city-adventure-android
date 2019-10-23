package pl.reconizer.unfold.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_adventure_time_length.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.prettyTimeStringRange
import pl.reconizer.unfold.common.extensions.toPrettyTimeString

class TimeLengthView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var minLength: Long? = null
        set(value) {
            if (value != field) {
                field = value
                updateTextView()
            }
        }

    var maxLength: Long? = null
        set(value) {
            if (value != field) {
                field = value
                updateTextView()
            }
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_time_length, this, true)
    }

    private fun updateTextView() {
        timeTextView.text = prettyTimeStringRange(minLength, maxLength)
    }
}