package pl.reconizer.unfold.presentation.puzzle.numberpushlock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_number_push_lock.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.customviews.ScalableSpace

class PushLock @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var numberOfRows: Int = 0
        set(value) {
            field = value
            initView()
        }

    private val _valuesStack: MutableList<Int> = mutableListOf()
    val valuesStack: List<Int>
        get() = _valuesStack

    private val buttons: MutableList<PushLockButton> = mutableListOf()

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_number_push_lock, this, true)
    }

    fun clearSelection() {
        _valuesStack.clear()
        buttons.forEach { it.isChecked = false }
    }

    private fun initView() {
        _valuesStack.clear()
        buttons.clear()
        when(numberOfRows) {
            3 -> lockBackground.setImageResource(R.drawable.puzzle_number_push_lock_3_bottom)
            4 -> lockBackground.setImageResource(R.drawable.puzzle_number_push_lock_4_bottom)
            else -> lockBackground.setImageResource(R.drawable.puzzle_number_push_lock_5_bottom)
        }
        for (i in 0 until numberOfRows) {
            buttonsContainer.addView(
                    createRow(i + 1)
            )
            if (i < numberOfRows - 2) {
                val rowMargin = ScalableSpace(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                            1,
                            resources.getDimensionPixelOffset(R.dimen.numberPushLockButtonsRowOffset)
                    )
                }
                buttonsContainer.addView(rowMargin)
            }
        }
    }

    private fun createRow(baseValue: Int): View {
        val rowContainer = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(
                        0, 0, 0, resources.getDimensionPixelOffset(R.dimen.numberPushLockButtonsRowOffset)
                )
            }
        }
        val leftButton = PushLockButton(context).apply {
            value = baseValue
            valueSide = PushLockButton.ValueSide.LEFT
            checkedChangeListener = {isChecked ->
                if (isChecked) {
                    _valuesStack.add(value)
                } else {
                    _valuesStack.remove(value)
                }
            }
            layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, resources.getDimensionPixelOffset(R.dimen.numberPushLockButtonsColumnOffset), 0)
            }
        }
        val rightButton = PushLockButton(context).apply {
            value = if (baseValue + numberOfRows > 2 * numberOfRows - 1) 0 else baseValue + numberOfRows
            valueSide = PushLockButton.ValueSide.RIGHT
            checkedChangeListener = {isChecked ->
                if (isChecked) {
                    _valuesStack.add(value)
                } else {
                    _valuesStack.remove(value)
                }
            }
            layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val columnMargin = ScalableSpace(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelOffset(R.dimen.numberPushLockButtonsColumnOffset),
                    1
            )
        }
        rowContainer.addView(leftButton)
        rowContainer.addView(columnMargin)
        rowContainer.addView(rightButton)
        buttons.apply {
            add(leftButton)
            add(rightButton)
        }
        return rowContainer
    }
}