package pl.reconizer.unfold.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_button_dropdown.view.*
import pl.reconizer.unfold.R

abstract class ButtonDropdown<T> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var dropdownMenu: PopupMenu? = null
        private set

    var onMenuItemClickListener: ((menuItemId: Int) -> Unit)? = null

    var onSelectionListener: ((item: T?) -> Unit)? = null

    var currentlySelectedItem: T? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_button_dropdown, this, true)
        triggerButton.setOnClickListener { handleClick() }
    }

    open fun initMenu(@MenuRes menuResId: Int) {
        dropdownMenu = PopupMenu(context, triggerButton).apply {
            menuInflater.inflate(menuResId, menu)
            setOnMenuItemClickListener {
                handleItemClick(it)
                true
            }
        }
    }

    fun initSelection(item: T) {
        dropdownMenu?.menu?.findItem(mapToMenuItemId(item))?.let {
            updateSelection(it)
        }
    }

    protected abstract fun mapToMenuItemId(item: T): Int

    protected abstract fun mapFromMenuItemId(@IdRes menuItemId: Int): T

    private fun handleClick() {
        dropdownMenu?.show()
    }

    private fun handleItemClick(item: MenuItem) {
        updateSelection(item)
        onMenuItemClickListener?.invoke(item.itemId)
        onSelectionListener?.invoke(currentlySelectedItem)
    }

    protected open fun updateSelection(item: MenuItem) {
        triggerButton.text = provideLabel(item)
        currentlySelectedItem = mapFromMenuItemId(item.itemId)
    }

    protected open fun provideLabel(item: MenuItem): String {
        return item.title.toString()
    }

}