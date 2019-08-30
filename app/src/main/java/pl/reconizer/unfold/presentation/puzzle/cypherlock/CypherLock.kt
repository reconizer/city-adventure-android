package pl.reconizer.unfold.presentation.puzzle.cypherlock

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_cypher_lock.view.*
import kotlinx.android.synthetic.main.view_number_push_lock.view.buttonsContainer
import kotlinx.android.synthetic.main.view_number_push_lock.view.lockBackground
import pl.reconizer.unfold.R

class CypherLock @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var numberOfRows: Int = 0
        set(value) {
            field = value
            initView()
        }

    val valuesStack: List<Int>
        get() = rows.map { it.value }

    private val rows: MutableList<CypherLockRow> = mutableListOf()

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_cypher_lock, this, true)
    }

    fun resetLock() {
        rows.forEachIndexed { idx, cypherLockRow ->
            cypherLockRow.value = idx
        }
    }

    private fun initView() {
        rows.clear()
        buttonsContainer.removeAllViews()
        when(numberOfRows) {
            3 -> lockBackground.setImageResource(R.drawable.puzzle_cypher_lock_3_background)
            4 -> lockBackground.setImageResource(R.drawable.puzzle_cypher_lock_4_background)
            5 -> lockBackground.setImageResource(R.drawable.puzzle_cypher_lock_5_background)
            6 -> lockBackground.setImageResource(R.drawable.puzzle_cypher_lock_6_background)
            else -> throw IllegalArgumentException("Invalid number of rows")
        }
        for (i in 0 until numberOfRows) {
            val row = createRow(i)
            rows.add(row)
            buttonsContainer.addView(row)

        }
    }

    private fun createRow(baseValue: Int): CypherLockRow {
        return CypherLockRow(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            ).apply {
                gravity = Gravity.END
            }
            value = baseValue
        }
    }
}