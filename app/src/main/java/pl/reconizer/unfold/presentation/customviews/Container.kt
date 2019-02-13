package pl.reconizer.unfold.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import pl.reconizer.unfold.R

abstract class Container @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val contentContainer: ViewGroup by lazy { findViewById<ViewGroup>(R.id.contentContainer) }

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