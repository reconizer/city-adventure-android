package pl.reconizer.unfold.presentation.puzzle.directionlock

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.puzzles.DirectionAnswerType

class DirectionLockAnswers @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var numberOfItems: Int = 0
        set(value) {
            field = value
            createView()
        }

    private val items: MutableList<DirectionLockAnswerItem> = mutableListOf()

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.directionLockAnswersContainerBackgroundColor))
    }

    val answers: List<DirectionAnswerType?>
        get() = items.map { it.direction }

    fun addAnswer(index: Int, direction: DirectionAnswerType) {
        if (index >= 0 && index < numberOfItems) {
            items[index].direction = direction
        }
    }

    fun reset() {
        items.forEach {
            it.direction = null
        }
    }

    private fun createView() {
        items.clear()
        removeAllViews()
        for (i in 0 until numberOfItems) {
            val item = DirectionLockAnswerItem(context).apply {
                id = View.generateViewId()
                layoutParams = LayoutParams(
                        resources.getDimensionPixelOffset(R.dimen.directionLockAnswerItemSize),
                        resources.getDimensionPixelOffset(R.dimen.directionLockAnswerItemSize)
                )
            }
            items.add(
                    item
            )
            addView(item)
        }
        val constraintSet = ConstraintSet().also { it.clone(this) }
        if (items.isNotEmpty()) {
            constraintSet.connect(items.first().id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraintSet.connect(items.last().id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            if (items.size > 1) {
                (1 until items.size).forEach { index ->
                    constraintSet.connect(items[index].id, ConstraintSet.START, items[index - 1].id, ConstraintSet.END)
                    constraintSet.connect(items[index - 1].id, ConstraintSet.END, items[index].id, ConstraintSet.START)
                }
            }

            items.forEach { directionLockAnswerItem ->
                constraintSet.connect(directionLockAnswerItem.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                constraintSet.connect(directionLockAnswerItem.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            }

            constraintSet.createHorizontalChain(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.RIGHT,
                    items.map { it.id }.toIntArray(),
                    null,
                    ConstraintSet.CHAIN_SPREAD)
        }

        constraintSet.applyTo(this)
    }

}