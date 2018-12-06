package pl.reconizer.cityadventure.presentation.adventure.journal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_journal_page.view.*
import pl.reconizer.cityadventure.R

class JournalPageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_journal_page, this, true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (childCount > 1) {
            val lastChild = getChildAt(childCount - 1)
            (lastChild.parent as ViewGroup?)?.let { lastChildParent ->
                lastChildParent.removeView(lastChild)
                contentContainer.addView(lastChild.apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                })
            }
        }
    }

}