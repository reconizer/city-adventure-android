package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_rating_stamp.view.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.common.extensions.clamp

class RatingStampView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var rating: Int = 5
        set(value) {
            field = clamp(value, 1, 5)
            ratingValue.text = field.toString()
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_rating_stamp, this, true)
    }
}