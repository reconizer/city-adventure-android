package pl.reconizer.cityadventure.presentation.adventure.journal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.view_journal_page.view.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.presentation.customviews.Container

class JournalPageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Container(context, attrs, defStyleAttr) {

    var turnLeftListener: (() -> Unit)? = null
    var turnRightListener: (() -> Unit)? = null

    var turnableRight: Boolean = true
        set(value) {
            field = value
            turnNextArrow.isGone = !value
        }

    var turnableLeft: Boolean = false
        set(value) {
            field = value
            turnPrevArrow.isGone = !value
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_journal_page, this, true)

        turnableRight = true
        turnableLeft = false

        turnNextArrow.setOnClickListener {
            turnRightListener?.invoke()
        }

        turnPrevArrow.setOnClickListener {
            turnLeftListener?.invoke()
        }
    }

}