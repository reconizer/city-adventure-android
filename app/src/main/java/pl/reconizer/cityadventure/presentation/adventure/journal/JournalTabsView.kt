package pl.reconizer.cityadventure.presentation.adventure.journal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_journal_tabs.view.*
import pl.reconizer.cityadventure.R

class JournalTabsView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var activeTab: Tabs = Tabs.PROGRESS
        set(value) {
            field = value
            updateView()
        }

    var tabChangeListener: ((tab: Tabs) -> Unit)? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_journal_tabs, this, true)

        progressTabButton.setOnClickListener {
            activeTab = Tabs.PROGRESS
        }

        descriptionTabButton.setOnClickListener {
            activeTab = Tabs.DESCRIPTION
        }
        updateView()
    }

    private fun updateView() {
        if (activeTab == Tabs.PROGRESS) {
            progressTabButton.setImageResource(R.drawable.journal_tab_progress_active)
            progressTabButton.translationZ = 1f
            descriptionTabButton.setImageResource(R.drawable.journal_tab_description_inactive)
            descriptionTabButton.translationZ = 0f
        } else {
            progressTabButton.setImageResource(R.drawable.journal_tab_progress_inactive)
            progressTabButton.translationZ = 0f
            descriptionTabButton.setImageResource(R.drawable.journal_tab_description_active)
            descriptionTabButton.translationZ = 1f
        }
        tabChangeListener?.invoke(activeTab)
    }

    enum class Tabs {
        PROGRESS,
        DESCRIPTION;
    }
}