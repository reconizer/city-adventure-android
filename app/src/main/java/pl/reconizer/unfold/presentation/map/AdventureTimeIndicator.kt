package pl.reconizer.unfold.presentation.map

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_adventure_time_indicator.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.toPrettyTimeStringFromSeconds

class AdventureTimeIndicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    // in milliseconds
    var time: Long = 0
        set(value) {
            field = value
            updateView()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_adventure_time_indicator, this, true)
    }

    private fun updateView() {
        timeTextView.text = (time / 1000).toPrettyTimeStringFromSeconds()
    }
}