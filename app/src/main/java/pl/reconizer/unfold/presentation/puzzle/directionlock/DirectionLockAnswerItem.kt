package pl.reconizer.unfold.presentation.puzzle.directionlock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_direction_lock_answer_item.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.puzzles.DirectionAnswerType

class DirectionLockAnswerItem @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var direction: DirectionAnswerType? = null
        set(value) {
            field = value
            updateView()
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_direction_lock_answer_item, this, true)

        updateView()
    }

    private fun updateView() {
        answerIndicator.isVisible = true
        answerPlaceholder.isVisible = false
        when (direction) {
            DirectionAnswerType.UP -> answerIndicator.rotation = 0f
            DirectionAnswerType.RIGHT -> answerIndicator.rotation = 90f
            DirectionAnswerType.DOWN -> answerIndicator.rotation = 180f
            DirectionAnswerType.LEFT -> answerIndicator.rotation = 270f
            null -> {
                answerIndicator.isVisible = false
                answerPlaceholder.isVisible = true
            }
        }
    }
}