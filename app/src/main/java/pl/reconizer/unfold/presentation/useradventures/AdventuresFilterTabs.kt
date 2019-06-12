package pl.reconizer.unfold.presentation.useradventures

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_user_adventures_tabs.view.*
import pl.reconizer.unfold.R

class AdventuresFilterTabs @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class Tab {
        STARTED,
        COMPLETED,
        PURCHASED
    }

    var currentTab = Tab.STARTED
        set(value) {
            val previousType = field
            field = value
            updateView(previousType, field)
            onTabChangeListener?.invoke(field)
        }

    var onTabChangeListener: ((tab: Tab) -> Unit)? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_user_adventures_tabs, this, true)

        completedTabBackground.setOnClickListener {
            currentTab = Tab.COMPLETED
        }

        startedTabBackground.setOnClickListener {
            currentTab = Tab.STARTED
        }

        purchasedTabBackground.setOnClickListener {
            currentTab = Tab.PURCHASED
        }

        updateView(currentTab, currentTab)
    }

    private fun updateView(oldTabType: Tab, newTabType: Tab) {
        when(oldTabType) {
            Tab.STARTED -> startedTabSpace.isVisible = true
            Tab.COMPLETED -> completedTabSpace.isVisible = true
            Tab.PURCHASED -> purchasedTabSpace.isVisible = true
        }

        when(newTabType) {
            Tab.STARTED -> startedTabSpace.isGone = true
            Tab.COMPLETED -> completedTabSpace.isGone = true
            Tab.PURCHASED -> purchasedTabSpace.isGone = true
        }
    }
}