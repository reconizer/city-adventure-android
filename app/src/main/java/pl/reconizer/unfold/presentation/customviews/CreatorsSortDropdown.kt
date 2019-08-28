package pl.reconizer.unfold.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.CreatorsSort

class CreatorsSortDropdown @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ButtonDropdown<CreatorsSort>(context, attrs, defStyleAttr) {

    init {
        initMenu(R.menu.creators_sort_menu)
        initSelection(CreatorsSort.RATING)
    }

    override fun mapToMenuItemId(item: CreatorsSort): Int {
        return when(item) {
            CreatorsSort.RANGE -> R.id.range
            CreatorsSort.RATING -> R.id.rating
        }
    }

    override fun mapFromMenuItemId(menuItemId: Int): CreatorsSort {
        return when(menuItemId) {
            R.id.range -> CreatorsSort.RANGE
            R.id.rating -> CreatorsSort.RATING
            else -> throw IllegalArgumentException("Unsupported menu item")
        }
    }

}