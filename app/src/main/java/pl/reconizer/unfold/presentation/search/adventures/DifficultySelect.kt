package pl.reconizer.unfold.presentation.search.adventures

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_difficulty_select.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.DifficultyLevel

class DifficultySelect @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var onSelectionChangedListener: ((selection: DifficultyLevel?) -> Unit)? = null

    var selection: DifficultyLevel? = null
        set(value) {
            field = value
            updateState()
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_difficulty_select, this, true)

        orientation = VERTICAL
        
        easyLevel.setOnClickListener {
            selection = if (easyLevel.isChecked) DifficultyLevel.EASY else null
            onSelectionChangedListener?.invoke(selection)
        }

        mediumLevel.setOnClickListener {
            selection = if (mediumLevel.isChecked) DifficultyLevel.MEDIUM else null
            onSelectionChangedListener?.invoke(selection)

        }

        hardLevel.setOnClickListener {
            selection = if (hardLevel.isChecked) DifficultyLevel.HARD else null
            onSelectionChangedListener?.invoke(selection)

        }
    }

    private fun updateState() {
        easyLevel?.isChecked = selection == DifficultyLevel.EASY
        mediumLevel?.isChecked = selection == DifficultyLevel.MEDIUM
        hardLevel?.isChecked = selection == DifficultyLevel.HARD
    }


}