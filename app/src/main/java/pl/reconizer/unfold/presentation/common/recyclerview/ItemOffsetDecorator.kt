package pl.reconizer.unfold.presentation.common.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil

class ItemOffsetDecorator(private val itemOffset: Int, private val offsetType: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetResId: Int, offsetType: Int = OFFSET_BOTTOM) : this(context.resources.getDimensionPixelSize(itemOffsetResId), offsetType)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        when (determineDisplayType(parent)) {
            DisplayType.LINEAR -> {
                outRect.set(
                        if (offsetType and OFFSET_LEFT != 0) itemOffset else 0,
                        if (offsetType and OFFSET_TOP != 0) itemOffset else 0,
                        if (offsetType and OFFSET_RIGHT != 0) itemOffset else 0,
                        if (offsetType and OFFSET_BOTTOM != 0) itemOffset else 0
                )
            }
            DisplayType.GRID -> {
                val position = parent.getChildViewHolder(view).adapterPosition
                val columns = (parent.layoutManager as GridLayoutManager).spanCount
                val currentColumn = position % columns
                val rows = ceil(1.0 * state.itemCount / columns).toInt()
                outRect.set(
                        if (offsetType and OFFSET_LEFT != 0) itemOffset - currentColumn * itemOffset / columns else 0,
                        if (offsetType and OFFSET_TOP != 0) itemOffset else 0,
                        if (offsetType and OFFSET_RIGHT != 0) (currentColumn + 1) * itemOffset / columns else 0,
                        if (offsetType and OFFSET_BOTTOM != 0) itemOffset else 0

                )
            }
        }
    }

    private fun determineDisplayType(recyclerView: RecyclerView): DisplayType {
        return if (recyclerView.layoutManager is GridLayoutManager) {
            DisplayType.GRID
        } else {
            DisplayType.LINEAR
        }
    }

    enum class DisplayType {
        LINEAR,
        GRID
    }

    companion object {
        const val OFFSET_BOTTOM = 1
        const val OFFSET_LEFT = 2
        const val OFFSET_TOP = 4
        const val OFFSET_RIGHT = 8
    }
}