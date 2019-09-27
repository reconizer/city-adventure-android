package pl.reconizer.unfold.presentation.puzzle.directionlock

import android.content.Context
import android.graphics.PointF
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.view_direction_lock.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.puzzles.DirectionAnswerType
import kotlin.math.abs
import kotlin.math.absoluteValue

class DirectionLock @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val valuesStack: List<DirectionAnswerType>
        get() = _valuesStack

    private val _valuesStack: MutableList<DirectionAnswerType> = mutableListOf()

    private var startPoint: PointF? = null
    private var previousTouchPoint = PointF(0f, 0f)

    private var previousSelectedDirection: DirectionAnswerType? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_direction_lock, this, true)

        lockKnob.setOnTouchListener { v: View?, event: MotionEvent? ->
            onKnobTouchEvent(event)
        }
    }

    private fun onKnobTouchEvent(event: MotionEvent?): Boolean {

        event?.let { certainEvent ->
            when (certainEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPoint = PointF(
                            certainEvent.rawX,
                            certainEvent.rawY
                    )
                }
                MotionEvent.ACTION_MOVE -> {
                    startPoint?.let {certainStartPoint ->
                        val eventPoint = PointF(
                                certainEvent.rawX,
                                certainEvent.rawY
                        )
                        Log.d("DirectionLock", "TOUCH: $eventPoint")
                        val knobMoveRadius = (lockBackground.width - lockKnob.width) / 2
                        val moveVector = PointF(
                                -(certainStartPoint.x - eventPoint.x),
                                -(certainStartPoint.y - eventPoint.y)
                        )

                        val absTouchChangeVector = PointF(
                                (eventPoint.x - previousTouchPoint.x).absoluteValue,
                                (eventPoint.y - previousTouchPoint.y).absoluteValue
                        )
                        if ((abs(moveVector.x) > knobMoveRadius * DEAD_ZONE || abs(moveVector.y) > knobMoveRadius * DEAD_ZONE) &&
                                (absTouchChangeVector.x > MINIMAL_CHANGE || absTouchChangeVector.y > MINIMAL_CHANGE)) {
                            previousTouchPoint = eventPoint

                            val knobMoveNormalisedVector = PointF(
                                    (abs(moveVector.x) / knobMoveRadius).coerceIn(0f, 1f),
                                    (abs(moveVector.y) / knobMoveRadius).coerceIn(0f, 1f)
                            )

                            var currentlySelectedDirection: DirectionAnswerType? = null

                            if (knobMoveNormalisedVector.x < RESET_THRESHOLD && knobMoveNormalisedVector.y < RESET_THRESHOLD) {
                                previousSelectedDirection = null
                            }

                            if(moveVector.x < 0 && moveVector.y >= 0) { // bottom left
                                if (abs(moveVector.x) > abs(moveVector.y)) { // left
                                    if (knobMoveNormalisedVector.x > ACCEPTANCE_THRESHOLD) {
                                        currentlySelectedDirection = DirectionAnswerType.LEFT
                                    }
                                    moveKnob(knobMoveNormalisedVector.x, DirectionAnswerType.LEFT)
                                } else { // bottom
                                    if (knobMoveNormalisedVector.y > ACCEPTANCE_THRESHOLD) {
                                        currentlySelectedDirection = DirectionAnswerType.DOWN
                                    }
                                    moveKnob(knobMoveNormalisedVector.y, DirectionAnswerType.DOWN)
                                }

                            } else if (moveVector.x >= 0 && moveVector.y >= 0) { // bottom right
                                if (abs(moveVector.x) > abs(moveVector.y)) { // right
                                    if (knobMoveNormalisedVector.x > ACCEPTANCE_THRESHOLD) {
                                        currentlySelectedDirection = DirectionAnswerType.RIGHT
                                    }
                                    moveKnob(knobMoveNormalisedVector.x, DirectionAnswerType.RIGHT)
                                } else { // bottom
                                    if (knobMoveNormalisedVector.y > ACCEPTANCE_THRESHOLD) {
                                        currentlySelectedDirection = DirectionAnswerType.DOWN
                                    }
                                    moveKnob(knobMoveNormalisedVector.y, DirectionAnswerType.DOWN)
                                }
                            } else if (moveVector.x < 0 && moveVector.y < 0) { // top left
                                if (abs(moveVector.x) > abs(moveVector.y)) { // left
                                    if (knobMoveNormalisedVector.x > ACCEPTANCE_THRESHOLD) {
                                        currentlySelectedDirection = DirectionAnswerType.LEFT
                                    }
                                    moveKnob(knobMoveNormalisedVector.x, DirectionAnswerType.LEFT)
                                } else { // top
                                    if (knobMoveNormalisedVector.y > ACCEPTANCE_THRESHOLD) {
                                        currentlySelectedDirection = DirectionAnswerType.UP
                                    }
                                    moveKnob(knobMoveNormalisedVector.y, DirectionAnswerType.UP)
                                }
                            } else if (moveVector.x >= 0 && moveVector.y < 0) { // top right
                                if (abs(moveVector.x) > abs(moveVector.y)) { // right
                                    if (knobMoveNormalisedVector.x > ACCEPTANCE_THRESHOLD) {
                                        currentlySelectedDirection = DirectionAnswerType.RIGHT
                                    }
                                    moveKnob(knobMoveNormalisedVector.x, DirectionAnswerType.RIGHT)
                                } else { // top
                                    if (knobMoveNormalisedVector.y > ACCEPTANCE_THRESHOLD) {
                                        currentlySelectedDirection = DirectionAnswerType.UP
                                    }
                                    moveKnob(knobMoveNormalisedVector.y, DirectionAnswerType.UP)
                                }
                            }

                            if (previousSelectedDirection != currentlySelectedDirection &&
                                    (knobMoveNormalisedVector.x > ACCEPTANCE_THRESHOLD || knobMoveNormalisedVector.y > ACCEPTANCE_THRESHOLD)) {
                                previousSelectedDirection = currentlySelectedDirection
                                _valuesStack.add(currentlySelectedDirection!!)

                                val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
                                vibrator?.vibrate(100)

                            }
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    startPoint = null
                    previousTouchPoint = PointF(0f, 0f)
                    moveKnob(0f, DirectionAnswerType.LEFT)
                }
            }
            certainEvent
        }
        return true
    }

    fun resetLock() {
        _valuesStack.clear()
    }

    private fun moveKnob(value: Float, direction: DirectionAnswerType) {
        Log.d("DirectionLock", "Movement: $value")
        val constraintSet = ConstraintSet().apply {
            clone(this@DirectionLock)
        }
        constraintSet.setVerticalBias(R.id.lockKnob, 0.5f)
        constraintSet.setHorizontalBias(R.id.lockKnob, 0.5f)
        when (direction) {
            DirectionAnswerType.LEFT -> {
                constraintSet.setHorizontalBias(R.id.lockKnob, 0.5f - 0.5f * value * MOVE_RANGE)
            }
            DirectionAnswerType.UP -> {
                constraintSet.setVerticalBias(R.id.lockKnob, 0.5f - 0.5f * value * MOVE_RANGE)
            }
            DirectionAnswerType.RIGHT -> {
                constraintSet.setHorizontalBias(R.id.lockKnob, 0.5f + 0.5f * value * MOVE_RANGE)
            }
            DirectionAnswerType.DOWN -> {
                constraintSet.setVerticalBias(R.id.lockKnob, 0.5f + 0.5f * value * MOVE_RANGE)
            }
        }
        constraintSet.applyTo(this)
    }

    companion object {
        private const val MOVE_RANGE = 0.7f
        private const val DEAD_ZONE = 0.05f
        private const val MINIMAL_CHANGE = 5
        private const val ACCEPTANCE_THRESHOLD = 0.9f
        private const val RESET_THRESHOLD = 0.5f
    }

}