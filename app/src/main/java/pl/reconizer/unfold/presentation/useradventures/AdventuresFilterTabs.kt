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

    var currentTab = UserAdventuresType.STARTED
        set(value) {
            val previousType = field
            field = value
            updateView(previousType, field)
            onTabChangeListener?.invoke(field)
        }

    var onTabChangeListener: ((tab: UserAdventuresType) -> Unit)? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_user_adventures_tabs, this, true)

        completedTabBackground.setOnClickListener {
            currentTab = UserAdventuresType.COMPLETED
        }

        startedTabBackground.setOnClickListener {
            currentTab = UserAdventuresType.STARTED
        }

        purchasedTabBackground.setOnClickListener {
            currentTab = UserAdventuresType.PURCHASED
        }

        updateView(currentTab, currentTab)
    }

    private fun updateView(oldTabType: UserAdventuresType, newTabType: UserAdventuresType) {
        when(oldTabType) {
            UserAdventuresType.STARTED -> startedTabSpace.isVisible = true
            UserAdventuresType.COMPLETED -> completedTabSpace.isVisible = true
            UserAdventuresType.PURCHASED -> purchasedTabSpace.isVisible = true
        }

        when(newTabType) {
            UserAdventuresType.STARTED -> startedTabSpace.isGone = true
            UserAdventuresType.COMPLETED -> completedTabSpace.isGone = true
            UserAdventuresType.PURCHASED -> purchasedTabSpace.isGone = true
        }
    }
}