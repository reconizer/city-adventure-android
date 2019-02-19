package pl.reconizer.unfold.presentation.common.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecorator(private val itemOffset: Int, private val offsetType: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetResId: Int, offsetType: Int = OFFSET_BOTTOM) : this(context.resources.getDimensionPixelSize(itemOffsetResId), offsetType)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(
                if (offsetType and OFFSET_LEFT != 0) itemOffset else 0,
                if (offsetType and OFFSET_TOP != 0) itemOffset else 0,
                if (offsetType and OFFSET_RIGHT != 0) itemOffset else 0,
                if (offsetType and OFFSET_BOTTOM != 0) itemOffset else 0
        )
    }

    companion object {
        const val OFFSET_BOTTOM = 1
        const val OFFSET_LEFT = 2
        const val OFFSET_TOP = 4
        const val OFFSET_RIGHT = 8
    }
}
