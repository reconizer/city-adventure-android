package pl.reconizer.cityadventure.presentation.puzzle.numberpushlock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getIntegerOrThrow
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.view_number_push_lock_button.view.*
import pl.reconizer.cityadventure.R

class PushLockButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Checkable {

    var value: Int = 0
        private set

    private var valueSide = 0

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_number_push_lock_button, this, true)


        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.PushLockButton)
            valueSide = typedArray.getInteger(R.styleable.PushLockButton_pushLockValueSide, 0)
            value = typedArray.getIntegerOrThrow(R.styleable.PushLockButton_integerValue)
            typedArray.recycle()
        }

        when (valueSide) {
            0 -> rightValueGroup.isGone = true
            1 -> leftValueGroup.isGone = true
        }
    }

    override fun isChecked(): Boolean {
        return button.isChecked
    }

    override fun toggle() {
        button.toggle()
    }

    override fun setChecked(checked: Boolean) {
        button.isChecked = checked
    }
}