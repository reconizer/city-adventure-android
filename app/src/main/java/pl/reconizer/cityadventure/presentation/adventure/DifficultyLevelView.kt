package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_adventure_difficulty_level.view.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.common.extensions.getDrawableResId
import pl.reconizer.cityadventure.common.extensions.getStringByName
import pl.reconizer.cityadventure.domain.entities.DifficultyLevel

class DifficultyLevelView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var level: DifficultyLevel = DifficultyLevel.EASY
        set(value) {
            if (value != field) {
                field = value
                updateLevel()
            }
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_difficulty_level, this, true)
    }

    private fun updateLevel() {
        val difficultyIconResId = resources.getDrawableResId(context, "difficulty_level_${level.name.toLowerCase()}")
        if (difficultyIconResId != null) {
            difficultyIcon.setImageResource(difficultyIconResId)
            difficultyLevelName.text = resources.getStringByName(context, "difficulty_level_${level.name.toLowerCase()}")
        }
    }
}