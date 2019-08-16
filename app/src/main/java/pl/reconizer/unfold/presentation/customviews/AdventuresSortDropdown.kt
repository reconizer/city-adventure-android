package pl.reconizer.unfold.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.AdventuresSort

class AdventuresSortDropdown @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ButtonDropdown<AdventuresSort>(context, attrs, defStyleAttr) {

    init {
        initMenu(R.menu.adventures_sort_menu)
        initSelection(AdventuresSort.RANGE)
    }

    override fun mapToMenuItemId(item: AdventuresSort): Int {
        return when(item) {
            AdventuresSort.RANGE -> R.id.range
            AdventuresSort.DIFFICULTY_LEVEL -> R.id.difficulty_level
            AdventuresSort.RATING -> R.id.rating
        }
    }

    override fun mapFromMenuItemId(menuItemId: Int): AdventuresSort {
        return when(menuItemId) {
            R.id.range -> AdventuresSort.RANGE
            R.id.difficulty_level -> AdventuresSort.DIFFICULTY_LEVEL
            R.id.rating -> AdventuresSort.RATING
            else -> throw IllegalArgumentException("Unsupported menu item")
        }
    }

}