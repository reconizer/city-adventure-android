package pl.reconizer.unfold.presentation.puzzle.directionlock

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.view_direction_lock.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.performOneShotVibration
import pl.reconizer.unfold.domain.entities.puzzles.DirectionAnswerType
import kotlin.math.abs
import kotlin.math.absoluteValue

class DirectionLock @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onNewDirectionListener: ((direction: DirectionAnswerType) -> Unit)? = null

    private var startPoint: PointF? = null
    private var previousTouchPoint = PointF(0f, 0f)
    private var previousMoveDirection: DirectionAnswerType? = null

    private var previousSelectedDirection: DirectionAnswerType? = null

    private var touchable: Boolean = true

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_direction_lock, this, true)

        lockKnob.setOnTouchListener { _: View?, event: MotionEvent? ->
            onKnobTouchEvent(event)
        }

    }

    fun reset() {
        touchable = false
        resetKnob()
    }

    private fun onKnobTouchEvent(event: MotionEvent?): Boolean {

        event?.let { certainEvent ->
            when (certainEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchable = true
                    startPoint = PointF(
                            certainEvent.rawX,
                            certainEvent.rawY
                    )
                }
                MotionEvent.ACTION_MOVE -> {
                    if (touchable) {
                        startPoint?.let { certainStartPoint ->
                            val eventPoint = PointF(
                                    certainEvent.rawX,
                                    certainEvent.rawY
                            )
                            val moveVector = PointF(
                                    -(certainStartPoint.x - eventPoint.x),
                                    -(certainStartPoint.y - eventPoint.y)
                            )

                            val absTouchChangeVector = PointF(
                                    (eventPoint.x - previousTouchPoint.x).absoluteValue,
                                    (eventPoint.y - previousTouchPoint.y).absoluteValue
                            )

                            val normalisedMoveVector = normalisedKnobMoveVector(moveVector)

                            if (normalisedMoveVector.x.absoluteValue <= DEAD_ZONE || normalisedMoveVector.y.absoluteValue <= DEAD_ZONE) {
                                previousMoveDirection = null
                            }

                            if (absTouchChangeVector.x > MINIMAL_CHANGE || absTouchChangeVector.y > MINIMAL_CHANGE) {
                                previousTouchPoint = eventPoint

                                var currentlySelectedDirection: DirectionAnswerType? = null

                                val moveDirection = determineMoveDirection(moveVector)

                                if (previousMoveDirection == null || moveDirection.isSameAxisAs(previousMoveDirection!!)) {
                                    moveKnob(moveVector, moveDirection)
                                } else {
                                    moveKnob(moveVector, previousMoveDirection)
                                }

                                if (isMovementAcceptedAsAnswer(moveVector, previousMoveDirection)) {
                                    currentlySelectedDirection = previousMoveDirection
                                }

                                if (hasMovedBelowResetThreshold(moveVector, previousMoveDirection)) {
                                    previousSelectedDirection = null
                                }

                                if (previousSelectedDirection != currentlySelectedDirection && currentlySelectedDirection != null) {
                                    previousSelectedDirection = currentlySelectedDirection

                                    context?.performOneShotVibration(100)
                                    currentlySelectedDirection?.let { onNewDirectionListener?.invoke(it) }

                                }
                            }
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    touchable = true
                    startPoint = null
                    previousTouchPoint = PointF(0f, 0f)
                    resetKnob()
                }
            }
            certainEvent
        }
        return true
    }

    private fun resetKnob() {
        previousMoveDirection = null
        animateReturn()
    }

    private fun moveKnob(moveVector: PointF, direction: DirectionAnswerType? = null) {
        previousMoveDirection = direction
        val normalisedMovePoint = normalisedKnobMoveVector(moveVector)
        val constraintSet = ConstraintSet().apply {
            clone(this@DirectionLock)
        }
        constraintSet.setVerticalBias(R.id.lockKnob, 0.5f)
        constraintSet.setHorizontalBias(R.id.lockKnob, 0.5f)
        when (direction) {
            DirectionAnswerType.LEFT -> {
                constraintSet.setHorizontalBias(R.id.lockKnob, 0.5f - 0.5f * normalisedMovePoint.x.absoluteValue * MOVE_RANGE)
            }
            DirectionAnswerType.UP -> {
                constraintSet.setVerticalBias(R.id.lockKnob, 0.5f - 0.5f * normalisedMovePoint.y.absoluteValue * MOVE_RANGE)
            }
            DirectionAnswerType.RIGHT -> {
                constraintSet.setHorizontalBias(R.id.lockKnob, 0.5f + 0.5f * normalisedMovePoint.x.absoluteValue * MOVE_RANGE)
            }
            DirectionAnswerType.DOWN -> {
                constraintSet.setVerticalBias(R.id.lockKnob, 0.5f + 0.5f * normalisedMovePoint.y.absoluteValue * MOVE_RANGE)
            }
        }
        constraintSet.applyTo(this)
    }

    private fun determineMoveDirection(moveVector: PointF): DirectionAnswerType {
        return if (moveVector.x < 0 && moveVector.y >= 0) {
            if (abs(moveVector.x) > abs(moveVector.y)) {
                DirectionAnswerType.LEFT
            } else {
                DirectionAnswerType.DOWN
            }
        } else if (moveVector.x >= 0 && moveVector.y >= 0) {
            if (abs(moveVector.x) > abs(moveVector.y)) {
                DirectionAnswerType.RIGHT
            } else {
                DirectionAnswerType.DOWN
            }
        } else if (moveVector.x < 0 && moveVector.y < 0) {
            if (abs(moveVector.x) > abs(moveVector.y)) {
                DirectionAnswerType.LEFT
            } else {
                DirectionAnswerType.UP
            }
        } else {
            if (abs(moveVector.x) > abs(moveVector.y)) {
                DirectionAnswerType.RIGHT
            } else {
                DirectionAnswerType.UP
            }
        }
    }

    private fun isMovementAcceptedAsAnswer(moveVector: PointF, moveDirection: DirectionAnswerType?): Boolean {
        val knobMoveNormalisedVector = normalisedKnobMoveVector(moveVector)

        return (knobMoveNormalisedVector.x.absoluteValue > ACCEPTANCE_THRESHOLD && (moveDirection == DirectionAnswerType.LEFT || moveDirection == DirectionAnswerType.RIGHT)) ||
                (knobMoveNormalisedVector.y.absoluteValue > ACCEPTANCE_THRESHOLD && (moveDirection == DirectionAnswerType.UP || moveDirection == DirectionAnswerType.DOWN))
    }

    private fun hasMovedBelowResetThreshold(moveVector: PointF, moveDirection: DirectionAnswerType?): Boolean {
        val knobMoveNormalisedVector = normalisedKnobMoveVector(moveVector)

        return (knobMoveNormalisedVector.x.absoluteValue < RESET_THRESHOLD && (moveDirection == DirectionAnswerType.LEFT || moveDirection == DirectionAnswerType.RIGHT)) ||
                (knobMoveNormalisedVector.y.absoluteValue < RESET_THRESHOLD && (moveDirection == DirectionAnswerType.UP || moveDirection == DirectionAnswerType.DOWN))
    }

    private fun normalisedKnobMoveVector(moveVector: PointF): PointF {
        val knobMoveRadius = (lockBackground.width - lockKnob.width) / 2
        return PointF(
                (abs(moveVector.x) / knobMoveRadius).coerceIn(-1f, 1f),
                (abs(moveVector.y) / knobMoveRadius).coerceIn(-1f, 1f)
        )
    }

    private fun animateReturn() {
        val transition = AutoTransition().apply {
            interpolator = LinearInterpolator()
            duration = RETURN_ANIMATION_DURATION
        }
        TransitionManager.beginDelayedTransition(this, transition)
        val constraintSet = ConstraintSet().apply {
            clone(this@DirectionLock)
        }
        constraintSet.setHorizontalBias(R.id.lockKnob, 0.5f)
        constraintSet.setVerticalBias(R.id.lockKnob, 0.5f)
        constraintSet.applyTo(this)
    }

    companion object {
        private const val MOVE_RANGE = 0.4f
        private const val DEAD_ZONE = 0.05f
        private const val MINIMAL_CHANGE = 2 // in pixels
        private const val ACCEPTANCE_THRESHOLD = 0.9f
        private const val RESET_THRESHOLD = 0.5f
        private const val RETURN_ANIMATION_DURATION = 100L
    }

}