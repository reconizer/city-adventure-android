package pl.reconizer.unfold.presentation.puzzle.cypherlock

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.view_cypher_lock_row.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.clamp
import pl.reconizer.unfold.common.extensions.performOneShotVibration
import java.lang.IllegalArgumentException

class CypherLockRow @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var value: Int = 0
        set(value) {
            field = clamp(value, MIN_VALUE, MAX_VALUE)

            updateValue()
        }

    private val gestureDetector: GestureDetectorCompat
    private val gestureDetectorListener = object : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val distanceX = e2.x - e1.x
            if (distanceX < 0) {
                increaseValue()
            } else {
                decreaseValue()
            }
            context.performOneShotVibration(VIBRATION_DURATION)
            return true
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            increaseValue()
            context.performOneShotVibration(VIBRATION_DURATION)
            return true
        }

    }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_cypher_lock_row, this, true)

        gestureDetector = GestureDetectorCompat(context, gestureDetectorListener)
    }

    private fun updateValue() {
        val valueResourceId = when (value) {
            0 -> R.drawable.puzzle_cypher_lock_0_button
            1 -> R.drawable.puzzle_cypher_lock_1_button
            2 -> R.drawable.puzzle_cypher_lock_2_button
            3 -> R.drawable.puzzle_cypher_lock_3_button
            4 -> R.drawable.puzzle_cypher_lock_4_button
            5 -> R.drawable.puzzle_cypher_lock_5_button
            6 -> R.drawable.puzzle_cypher_lock_6_button
            7 -> R.drawable.puzzle_cypher_lock_7_button
            8 -> R.drawable.puzzle_cypher_lock_8_button
            9 -> R.drawable.puzzle_cypher_lock_9_button
            else -> throw IllegalArgumentException("Invalid push lock value")
        }
        row.setImageResource(valueResourceId)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun increaseValue() {
        if (value < 9) {
            value++
        } else {
            value = 0
        }
    }

    private fun decreaseValue() {
        if (value > 0) {
            value--
        } else {
            value = 9
        }
    }

    companion object {
        private const val MIN_VALUE = 0
        private const val MAX_VALUE = 9

        private const val VIBRATION_DURATION = 25L
    }

}