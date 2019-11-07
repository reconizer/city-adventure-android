package pl.reconizer.unfold.presentation.search.adventures

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_difficulty_select.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.addIfNotExists
import pl.reconizer.unfold.domain.entities.DifficultyLevel

class DifficultySelect @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var onSelectionChangedListener: ((selection: List<DifficultyLevel>) -> Unit)? = null

    var selection: List<DifficultyLevel> = emptyList()
        set(value) {
            field = value
            updateState()
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_difficulty_select, this, true)

        orientation = VERTICAL
        
        easyLevel.setOnClickListener {
            handleClickOnLevel(DifficultyLevel.EASY, easyLevel.isChecked)
        }

        mediumLevel.setOnClickListener {
            handleClickOnLevel(DifficultyLevel.MEDIUM, mediumLevel.isChecked)

        }

        hardLevel.setOnClickListener {
            handleClickOnLevel(DifficultyLevel.HARD, hardLevel.isChecked)
        }
    }

    private fun updateState() {
        easyLevel?.isChecked = DifficultyLevel.EASY in selection
        mediumLevel?.isChecked = DifficultyLevel.MEDIUM in selection
        hardLevel?.isChecked = DifficultyLevel.HARD in selection
    }

    private fun handleClickOnLevel(level: DifficultyLevel, isChecked: Boolean) {
        selection = if (isChecked) {
            selection.toMutableList().apply {
                addIfNotExists(level)
            }
        } else {
            selection.toMutableList().apply {
                remove(level)
            }
        }
        onSelectionChangedListener?.invoke(selection)
    }

}