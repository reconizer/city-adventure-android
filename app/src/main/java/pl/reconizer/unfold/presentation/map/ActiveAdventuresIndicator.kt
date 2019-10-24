package pl.reconizer.unfold.presentation.map

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_active_adventures_indicator.view.*
import pl.reconizer.unfold.R

class ActiveAdventuresIndicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var number: Int = 0
        set(value) {
            field = value
            updateView()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_active_adventures_indicator, this, true)
    }

    private fun updateView() {
        numberTextView.text = number.toString()
    }
}