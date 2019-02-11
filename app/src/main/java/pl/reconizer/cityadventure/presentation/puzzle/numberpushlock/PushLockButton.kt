package pl.reconizer.cityadventure.presentation.puzzle.numberpushlock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getIntegerOrThrow
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_number_push_lock_button.view.*
import pl.reconizer.cityadventure.R
import java.lang.IllegalArgumentException

class PushLockButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Checkable {

    var value: Int = 0
        set(value) {
            field = value
            updateValue()
        }

    var valueSide: ValueSide = ValueSide.LEFT
        set(value) {
            field = value
            updateValueSide()
        }

    var checkedChangeListener: ((isChecked: Boolean) -> Unit)? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_number_push_lock_button, this, true)

        rightValueGroup.isGone = true
        leftValueGroup.isGone = true

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.PushLockButton)
            valueSide = ValueSide.fromInt(typedArray.getInteger(R.styleable.PushLockButton_pushLockValueSide, 0))
            value = typedArray.getIntegerOrThrow(R.styleable.PushLockButton_integerValue)
            typedArray.recycle()
        }

        button.setOnCheckedChangeListener { _, isChecked -> checkedChangeListener?.invoke(isChecked) }
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

    private fun updateValueSide() {
        when (valueSide) {
            ValueSide.LEFT -> {
                leftValueGroup.isVisible = true
                rightValueGroup.isGone = true
            }
            ValueSide.RIGHT -> {
                leftValueGroup.isGone = true
                rightValueGroup.isVisible = true
            }
        }

    }

    private fun updateValue() {
        val valueResourceId = when (value) {
            0 -> R.drawable.puzzle_number_push_lock_0
            1 -> R.drawable.puzzle_number_push_lock_1
            2 -> R.drawable.puzzle_number_push_lock_2
            3 -> R.drawable.puzzle_number_push_lock_3
            4 -> R.drawable.puzzle_number_push_lock_4
            5 -> R.drawable.puzzle_number_push_lock_5
            6 -> R.drawable.puzzle_number_push_lock_6
            7 -> R.drawable.puzzle_number_push_lock_7
            8 -> R.drawable.puzzle_number_push_lock_8
            9 -> R.drawable.puzzle_number_push_lock_9
            else -> throw IllegalArgumentException("Invalid push lock value")
        }
        leftValue.setImageResource(valueResourceId)
        rightValue.setImageResource(valueResourceId)
    }

    enum class ValueSide {
        LEFT,
        RIGHT;

        companion object {
            fun fromInt(value: Int): ValueSide {
                return when(value) {
                    0 -> LEFT
                    1 -> RIGHT
                    else -> LEFT
                }
            }
        }
    }

}