package pl.reconizer.cityadventure.presentation.adventure.journal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.view_journal_page.view.*
import pl.reconizer.cityadventure.R

class JournalPageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var turnable: Boolean = true
        set(value) {
            field = value
            turnArrow.isGone = !value
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_journal_page, this, true)

        turnable = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (childCount > 1) {
            val lastChild = getChildAt(childCount - 1)
            (lastChild.parent as ViewGroup?)?.let { lastChildParent ->
                lastChildParent.removeView(lastChild)
                addViewToPage(lastChild.apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                })
            }
        }
    }

    fun addViewToPage(child: View?) { contentContainer.addView(child) }

    fun removeViewFromPage(view: View?) { contentContainer.removeView(view) }

}