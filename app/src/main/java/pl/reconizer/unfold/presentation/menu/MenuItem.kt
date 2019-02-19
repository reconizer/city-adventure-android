package pl.reconizer.unfold.presentation.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_menu_item.view.*
import pl.reconizer.unfold.R

class MenuItem @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_menu_item, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.MenuItem)
            menuItem.text = typedArray.getString(R.styleable.MenuItem_android_text)
            typedArray.recycle()
        }
    }

}